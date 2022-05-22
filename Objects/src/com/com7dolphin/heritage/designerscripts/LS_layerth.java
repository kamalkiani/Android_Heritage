package com.com7dolphin.heritage.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layerth{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="mainPanel.Left = 0"[layerth/General script]
views.get("mainpanel").vw.setLeft((int)(0d));
//BA.debugLineNum = 3;BA.debugLine="mainPanel.Top = 0"[layerth/General script]
views.get("mainpanel").vw.setTop((int)(0d));
//BA.debugLineNum = 4;BA.debugLine="mainPanel.Width = 100%x"[layerth/General script]
views.get("mainpanel").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 5;BA.debugLine="mainPanel.Height = 100%y"[layerth/General script]
views.get("mainpanel").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 7;BA.debugLine="topImg.Left = 0"[layerth/General script]
views.get("topimg").vw.setLeft((int)(0d));
//BA.debugLineNum = 8;BA.debugLine="topImg.Top = 0"[layerth/General script]
views.get("topimg").vw.setTop((int)(0d));
//BA.debugLineNum = 9;BA.debugLine="topImg.Width = 100%x"[layerth/General script]
views.get("topimg").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 10;BA.debugLine="topImg.Height = 10%y"[layerth/General script]
views.get("topimg").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 12;BA.debugLine="homeImg.Left = 1%x"[layerth/General script]
views.get("homeimg").vw.setLeft((int)((1d / 100 * width)));
//BA.debugLineNum = 13;BA.debugLine="homeImg.Top = 1%y"[layerth/General script]
views.get("homeimg").vw.setTop((int)((1d / 100 * height)));
//BA.debugLineNum = 14;BA.debugLine="homeImg.Width = 8%y"[layerth/General script]
views.get("homeimg").vw.setWidth((int)((8d / 100 * height)));
//BA.debugLineNum = 15;BA.debugLine="homeImg.Height = 8%y"[layerth/General script]
views.get("homeimg").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 17;BA.debugLine="menuImg.Left = 99%x - 8%y"[layerth/General script]
views.get("menuimg").vw.setLeft((int)((99d / 100 * width)-(8d / 100 * height)));
//BA.debugLineNum = 18;BA.debugLine="menuImg.Top = 1%y"[layerth/General script]
views.get("menuimg").vw.setTop((int)((1d / 100 * height)));
//BA.debugLineNum = 19;BA.debugLine="menuImg.Width = 8%y"[layerth/General script]
views.get("menuimg").vw.setWidth((int)((8d / 100 * height)));
//BA.debugLineNum = 20;BA.debugLine="menuImg.Height = 8%y"[layerth/General script]
views.get("menuimg").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 22;BA.debugLine="titleLabel.Left = 25%x"[layerth/General script]
views.get("titlelabel").vw.setLeft((int)((25d / 100 * width)));
//BA.debugLineNum = 23;BA.debugLine="titleLabel.Top = 1%y"[layerth/General script]
views.get("titlelabel").vw.setTop((int)((1d / 100 * height)));
//BA.debugLineNum = 24;BA.debugLine="titleLabel.Width = 50%x"[layerth/General script]
views.get("titlelabel").vw.setWidth((int)((50d / 100 * width)));
//BA.debugLineNum = 25;BA.debugLine="titleLabel.Height = 8%y"[layerth/General script]
views.get("titlelabel").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 27;BA.debugLine="Label1.Left =10%x"[layerth/General script]
views.get("label1").vw.setLeft((int)((10d / 100 * width)));
//BA.debugLineNum = 28;BA.debugLine="Label1.Top = 11%y"[layerth/General script]
views.get("label1").vw.setTop((int)((11d / 100 * height)));
//BA.debugLineNum = 29;BA.debugLine="Label1.Width = 80%x"[layerth/General script]
views.get("label1").vw.setWidth((int)((80d / 100 * width)));
//BA.debugLineNum = 30;BA.debugLine="Label1.Height = 27%y"[layerth/General script]
views.get("label1").vw.setHeight((int)((27d / 100 * height)));
//BA.debugLineNum = 32;BA.debugLine="Button1.Left =30%x"[layerth/General script]
views.get("button1").vw.setLeft((int)((30d / 100 * width)));
//BA.debugLineNum = 33;BA.debugLine="Button1.Top = 40%y"[layerth/General script]
views.get("button1").vw.setTop((int)((40d / 100 * height)));
//BA.debugLineNum = 34;BA.debugLine="Button1.Width = 40%x"[layerth/General script]
views.get("button1").vw.setWidth((int)((40d / 100 * width)));
//BA.debugLineNum = 35;BA.debugLine="Button1.Height = 40%x"[layerth/General script]
views.get("button1").vw.setHeight((int)((40d / 100 * width)));
//BA.debugLineNum = 37;BA.debugLine="Label2.Left =10%x"[layerth/General script]
views.get("label2").vw.setLeft((int)((10d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="Label2.Top = 43%y + 40%x"[layerth/General script]
views.get("label2").vw.setTop((int)((43d / 100 * height)+(40d / 100 * width)));
//BA.debugLineNum = 39;BA.debugLine="Label2.Width = 80%x"[layerth/General script]
views.get("label2").vw.setWidth((int)((80d / 100 * width)));
//BA.debugLineNum = 40;BA.debugLine="Label2.Height = 23%y"[layerth/General script]
views.get("label2").vw.setHeight((int)((23d / 100 * height)));

}
}