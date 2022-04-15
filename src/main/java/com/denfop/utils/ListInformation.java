package com.denfop.utils;

import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class ListInformation {
    public static final List<String> panelinform = new ArrayList<>();
    public static final List<String> storageinform = new ArrayList<>();
    public static final List<String> fisherinform = new ArrayList<>();
    public static final List<String> analyzeinform = new ArrayList<>();
    public static final List<String> quarryinform = new ArrayList<>();

    public static void init() {
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation1"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation2"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation3"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation4"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation5"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation6"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation7"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation8"));
        quarryinform.add(StatCollector.translateToLocal("iu.quarryinformation9"));
        fisherinform.add(StatCollector.translateToLocal("iu.fisherinformation1"));
        fisherinform.add(StatCollector.translateToLocal("iu.fisherinformation2"));
        fisherinform.add(StatCollector.translateToLocal("iu.fisherinformation3"));

        panelinform.add(StatCollector.translateToLocal("iu.panelinformation1"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation2"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation3"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation4"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation5"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation6"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation7"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation8"));
        panelinform.add(StatCollector.translateToLocal("iu.panelinformation9"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation1"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation2"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation3"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation4"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation5"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation6"));
        storageinform.add(StatCollector.translateToLocal("iu.electricstorageinformation7"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation1"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation2"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation3"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation4"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation5"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation6"));
        analyzeinform.add(StatCollector.translateToLocal("iu.analyzerinformation7"));
    }
}
