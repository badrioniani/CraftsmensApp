package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Mechanic
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconChat
import org.example.project.ui.icons.IconPhone
import org.example.project.ui.icons.IconPin
import org.example.project.ui.icons.IconShield
import org.example.project.ui.icons.IconStar
import org.example.project.ui.theme.CraftsmenColors
import org.example.project.ui.util.rememberContactActions

private val VipGold = Color(0xFFF59E0B)

@Composable
fun MechanicListCard(
    theme: CraftsmenColors,
    mech: Mechanic,
    onClick: () -> Unit,
) {
    val s = LocalStrings.current
    val contact = rememberContactActions()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(theme.bgCard)
            .border(
                width = if (mech.vip) 1.5.dp else 1.dp,
                color = if (mech.vip) VipGold else theme.border,
                shape = RoundedCornerShape(18.dp),
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        if (mech.vip) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = 10.dp),
            ) {
                IconStar(size = 11.dp, color = VipGold)
                Text("VIP", color = VipGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(mech.photo),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = mech.initials,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = mech.shop,
                        color = theme.text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    if (mech.verified) {
                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(theme.accentSoft)
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            IconShield(size = 9.dp, color = theme.accent)
                            Text(
                                text = s.badgeVerified,
                                color = theme.accent,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.4.sp,
                                maxLines = 1,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = mech.name,
                    color = theme.textDim,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconPin(size = 11.dp, color = theme.textMute)
                    Text(
                        text = "${mech.district}, ${mech.city}",
                        color = theme.textDim,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    IconStar(size = 13.dp, color = theme.accent)
                    Text(
                        text = mech.rating.toString(),
                        color = theme.text,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Text(
                    text = "(${mech.reviews})",
                    color = theme.textMute,
                    fontSize = 10.sp,
                )
            }
        }

        if (mech.bio.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = mech.bio,
                color = theme.textDim,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, RoundedCornerShape(12.dp))
                    .clickable { contact.call(mech.phone) }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                IconPhone(size = 13.dp, color = theme.text)
                Spacer(Modifier.size(6.dp))
                Text(
                    text = s.cardCall,
                    color = theme.text,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(theme.bgRaised)
                    .border(1.dp, theme.border, RoundedCornerShape(12.dp))
                    .clickable { contact.whatsapp(mech.whatsapp.ifBlank { mech.phone }) }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                IconChat(size = 13.dp, color = theme.text)
                Spacer(Modifier.size(6.dp))
                Text(
                    text = s.cardWhatsapp,
                    color = theme.text,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
