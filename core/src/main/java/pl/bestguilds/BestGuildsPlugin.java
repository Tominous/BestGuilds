package pl.bestguilds;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.bestguilds.api.BestGuildsAPI;
import pl.bestguilds.api.command.BukkitCommandInjector;
import pl.bestguilds.api.command.CommandInjector;
import pl.bestguilds.api.command.CommandManager;
import pl.bestguilds.api.command.CommandManagerImpl;
import pl.bestguilds.api.guild.GuildManager;
import pl.bestguilds.api.user.UserManager;
import pl.bestguilds.command.GuildCommand;
import pl.bestguilds.command.sub.CreateGuildCommand;
import pl.bestguilds.guild.GuildManagerImpl;
import pl.bestguilds.user.UserManagerImpl;

public final class BestGuildsPlugin extends JavaPlugin implements BestGuildsAPI {

    private UserManager userManager;
    private GuildManager guildManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.userManager = new UserManagerImpl();
        this.guildManager = new GuildManagerImpl();
        this.commandManager = new CommandManagerImpl();

        CommandInjector commandInjector = new BukkitCommandInjector(this);
        registerCommands(commandInjector);

        BestGuilds.setInstance(this);
    }

    private void registerCommands(@NotNull CommandInjector injector) {
        this.commandManager.setMainCommand(new GuildCommand(this));
        this.commandManager.register(
                new CreateGuildCommand(this)
        );
        injector.inject();
    }

    @Override
    public @NotNull UserManager getUserManager() {
        return userManager;
    }

    @Override
    public @NotNull GuildManager getGuildManager() {
        return guildManager;
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }
}
