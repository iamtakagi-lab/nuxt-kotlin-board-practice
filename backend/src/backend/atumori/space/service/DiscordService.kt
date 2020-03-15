package backend.atumori.space.service

import backend.atumori.space.model.Report
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus

@KtorExperimentalAPI
class DiscordService(config: ApplicationConfig) {

    var jda: JDA = JDABuilder(AccountType.BOT).setToken(config.property("discord.token").getString()).setStatus(OnlineStatus.ONLINE).build()
    val GUILD_ID = config.property("discord.guild_id").getString()
    val REPORT_CHANNEL_ID = config.property("discord.report_channel_id").getString()

    fun sendReport(report: Report){
        jda.getGuildById(GUILD_ID).getTextChannelById(REPORT_CHANNEL_ID).sendMessage(report.toDiscordText()).queue()
    }
}