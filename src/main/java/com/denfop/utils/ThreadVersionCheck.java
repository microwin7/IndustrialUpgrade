package com.denfop.utils;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.denfop.Config;
import com.denfop.Constants;

public class ThreadVersionCheck extends Thread
{
    private int count = 0;

    public static int remoteMajVer;
    public static int remoteMinVer;
    public static int remoteBuildVer;

    public ThreadVersionCheck()
    {
        super("IndustrialUpgrade Version Check Thread");
    }

    @Override
    public void run()
    {
        final Side sideToCheck = FMLCommonHandler.instance().getSide();

        if (sideToCheck == null || Config.disableUpdateCheck)
        {
            return;
        }

        while (this.count < 3 && remoteBuildVer == 0)
        {
        	BufferedReader in = null;
            try
            {
                final URL url = new URL("https://github.com/ZelGimi/supersolarpanels/blob/dev-1.7.10-1.5.x/version");
                final HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.addRequestProperty("User-Agent", "Mozilla/4.76");
                InputStreamReader streamReader = new InputStreamReader(http.getInputStream());
                in = new BufferedReader(streamReader);
                String str;
                String[] str2;

                while ((str = in.readLine()) != null)
                {

                    if (str.contains("Version"))
                    {
                        str = str.replace("Version=", "");

                        str2 = str.split("#");

                        if (str2.length == 3)
                        {
                            remoteMajVer = Integer.parseInt(str2[0]);
                            remoteMinVer = Integer.parseInt(str2[1]);
                            remoteBuildVer = Integer.parseInt(str2[2]);
                        }

                        if (remoteMajVer == Constants.LOCALMAJVERSION && (remoteMinVer > Constants.LOCALMINVERSION || (remoteMinVer == Constants.LOCALMINVERSION && remoteBuildVer > Constants.LOCALBUILDVERSION)))
                        {


                            if (sideToCheck.equals(Side.CLIENT))
                            {
                                FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "New " + EnumChatFormatting.DARK_AQUA + Constants.MOD_NAME + EnumChatFormatting.GRAY + " version available! v" + remoteMajVer + "." + remoteMinVer + "." + remoteBuildVer + EnumChatFormatting.DARK_BLUE + " https://www.curseforge.com/minecraft/mc-mods/industrial-upgrade/"));
                            }
                            else if (sideToCheck.equals(Side.SERVER))
                            {
                                ModUtils.severe("New IndustrialUpgrade version available! v" + remoteMajVer + "." + remoteMinVer + "." + remoteBuildVer + " https://www.curseforge.com/minecraft/mc-mods/industrial-upgrade/");
                            }
                        }
                    }
                }
                
                in.close();
                streamReader.close();
            }
            catch (final Exception e)
            {
            	if (in != null)
            	{
                	try 
                	{
						in.close();
					} 
                	catch (IOException e1) 
                	{
						e1.printStackTrace();
					}
            	}
            }

            if (remoteBuildVer == 0)
            {

                	ModUtils.severe(StatCollector.translateToLocal("newversion.failed.name"));


                }

            else
            {
            	ModUtils.info(StatCollector.translateToLocal("newversion.success.name") + " " + remoteMajVer + "." + remoteMinVer + "." + remoteBuildVer);
            }

            this.count++;
        }
    }
}
