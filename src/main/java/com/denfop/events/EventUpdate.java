package com.denfop.events;

import com.denfop.Constants;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class EventUpdate {
    private final UpdateCheckThread thread;

    private int delay = 40;

    private boolean playerNotified = false;

    public EventUpdate() {
        this.thread = new UpdateCheckThread();
        this.thread.start();
    }

    @SubscribeEvent
    public void tickStart(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        if (this.delay > 0) {
            this.delay--;
            return;
        }
        if (!this.playerNotified && this.thread.isComplete()) {
            this.playerNotified = true;
            FMLCommonHandler.instance().bus().unregister(this);
            if (this.thread.getVersion().equals(Constants.MOD_VERSION)) {

                return;
            }
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + StatCollector.translateToLocal("updatemod4") + " " + EnumChatFormatting.RESET + EnumChatFormatting.BOLD + StatCollector.translateToLocal("updatemod") + EnumChatFormatting.RESET + EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + this.thread.getVersion()));
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "[IU] " + StatCollector.translateToLocal("updatemod5")));
            event.player.addChatComponentMessage(new ChatComponentText(this.thread.getChangelog()));
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "[IU] " + StatCollector.translateToLocal("updatemod2")));
            event.player.addChatComponentMessage(new ChatComponentText(this.thread.getDownload()));


        } else if (this.thread.isFailed()) {
            this.playerNotified = true;
            FMLCommonHandler.instance().bus().unregister(this);
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("updatemod4") + EnumChatFormatting.RED + StatCollector.translateToLocal("updatemod3")));
            if (!StringUtils.isNullOrEmpty(this.thread.getNote()[0]))
                event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + this.thread.getNote()[0]));
        }
    }

    public static class UpdateCheckThread extends Thread {
        private final String[] note = new String[5];
        private String version = null;
        private boolean complete = false;

        private boolean failed = false;
        private String changelogurl = null;
        private String download = null;

        public void run() {
            ModUtils.info("[Update Checker] Thread Started");
            try {
                URL versionURL = new URL("https://raw.githubusercontent.com/ZelGimi/industrialupgrade/1.7.10-dev/version.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(versionURL.openStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(":")) {
                        String value = line.substring(line.indexOf(":") + 1);
                        if (line.contains("Version")) {
                            this.version = value;
                            continue;
                        }
                        if (line.contains("CurseForge")) {
                            this.download = value;
                            continue;
                        }
                        if (line.contains("Changelog")) {

                            String url = "https://raw.githubusercontent.com/ZelGimi/industrialupgrade/1.7.10-dev/" + value;
                            URL ChangelogURL = new URL(url);
                            changelogurl = "https://raw.githubusercontent.com/ZelGimi/industrialupgrade/1.7.10-dev/changelog.txt";
                            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(ChangelogURL.openStream()));
                            String line1;
                            boolean getversion = false;
                            while ((line1 = bufferedReader1.readLine()) != null) {

                                if (line1.contains("#"))
                                    continue;
                                if (line1.contains(":")) {
                                    String value1 = line1.substring(line1.indexOf(":") + 1);
                                    getversion = this.version.equals(value1);

                                    continue;
                                }
                                if (getversion) {
                                    String value1 = line1.substring(line1.indexOf(".") + 1);
                                    if (note[0] == null) {
                                        this.note[0] = "- " + value1;
                                        continue;
                                    }
                                    if (note[1] == null) {
                                        this.note[1] = "- " + value1;
                                        continue;
                                    }

                                    if (note[2] == null) {
                                        this.note[2] = "- " + value1;
                                        continue;
                                    }
                                    if (note[3] == null) {
                                        this.note[3] = "- " + value1;
                                        continue;
                                    }
                                    if (note[4] == null) {
                                        this.note[4] = "- " + value1;
                                    }
                                }
                            }


                        }

                    }


                }
                if (this.download != null && this.version != null) {
                    this.complete = true;
                } else {
                    this.note[0] = "[Invalid Response]";
                    this.failed = true;
                }
                ModUtils.info("[Update Checker] Thread Finished");
            } catch (Exception e) {
                ModUtils.info("[Update Checker] Check Failed");
                this.failed = true;
                this.note[0] = e.getClass().toString();
                e.printStackTrace();
            }
        }

        public String getVersion() {
            return this.version;
        }

        public String getChangelog() {
            return this.changelogurl;
        }

        public String getDownload() {
            return this.download;
        }

        public String[] getNote() {
            return this.note;
        }

        public boolean isComplete() {
            return this.complete;
        }

        public boolean isFailed() {
            return this.failed;
        }
    }
}
