package com.com7dolphin.heritage.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layhome{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("welcomepanel").vw.setWidth((int)((100d / 100 * width)));
views.get("welcomepanel").vw.setHeight((int)((100d / 100 * height)));
views.get("welcomepanel").vw.setLeft((int)(0d));
views.get("welcomepanel").vw.setTop((int)(0d));
views.get("welcomeimg").vw.setWidth((int)((20d / 100 * height)));
views.get("welcomeimg").vw.setHeight((int)((20d / 100 * height)));
views.get("welcomeimg").vw.setLeft((int)((50d / 100 * width)-(10d / 100 * height)));
views.get("welcomeimg").vw.setTop((int)((40d / 100 * height)));
views.get("weblabel").vw.setWidth((int)((100d / 100 * width)));
views.get("weblabel").vw.setHeight((int)((10d / 100 * height)));
views.get("weblabel").vw.setLeft((int)(0d));
views.get("weblabel").vw.setTop((int)((65d / 100 * height)));
views.get("homepanel").vw.setWidth((int)((100d / 100 * width)));
views.get("homepanel").vw.setHeight((int)((100d / 100 * height)));
views.get("homepanel").vw.setLeft((int)(0d));
views.get("homepanel").vw.setTop((int)(0d));
views.get("headerimg").vw.setWidth((int)((100d / 100 * width)));
views.get("headerimg").vw.setHeight((int)((68d / 100 * height)));
views.get("headerimg").vw.setLeft((int)(0d));
views.get("headerimg").vw.setTop((int)(0d));
views.get("button2").vw.setWidth((int)((100d / 100 * width)));
views.get("button2").vw.setHeight((int)((8d / 100 * height)));
views.get("button2").vw.setLeft((int)(0d));
views.get("button2").vw.setTop((int)((68d / 100 * height)));
views.get("button3").vw.setWidth((int)((100d / 100 * width)));
views.get("button3").vw.setHeight((int)((8d / 100 * height)));
views.get("button3").vw.setLeft((int)(0d));
views.get("button3").vw.setTop((int)((76d / 100 * height)));
views.get("button4").vw.setWidth((int)((100d / 100 * width)));
views.get("button4").vw.setHeight((int)((8d / 100 * height)));
views.get("button4").vw.setLeft((int)(0d));
//BA.debugLineNum = 43;BA.debugLine="button4.Top = 84%y"[layhome/General script]
views.get("button4").vw.setTop((int)((84d / 100 * height)));
//BA.debugLineNum = 45;BA.debugLine="button5.Width = 100%x"[layhome/General script]
views.get("button5").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 46;BA.debugLine="button5.Height = 8%y"[layhome/General script]
views.get("button5").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 47;BA.debugLine="button5.Left = 0"[layhome/General script]
views.get("button5").vw.setLeft((int)(0d));
//BA.debugLineNum = 48;BA.debugLine="button5.Top = 92%y"[layhome/General script]
views.get("button5").vw.setTop((int)((92d / 100 * height)));
//BA.debugLineNum = 50;BA.debugLine="Label2.Width = 95%x"[layhome/General script]
views.get("label2").vw.setWidth((int)((95d / 100 * width)));
//BA.debugLineNum = 51;BA.debugLine="Label2.Height = 8%y"[layhome/General script]
views.get("label2").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 52;BA.debugLine="Label2.Left = 0"[layhome/General script]
views.get("label2").vw.setLeft((int)(0d));
//BA.debugLineNum = 53;BA.debugLine="Label2.Top = 68%y"[layhome/General script]
views.get("label2").vw.setTop((int)((68d / 100 * height)));
//BA.debugLineNum = 55;BA.debugLine="Label3.Width = 95%x"[layhome/General script]
views.get("label3").vw.setWidth((int)((95d / 100 * width)));
//BA.debugLineNum = 56;BA.debugLine="Label3.Height = 8%y"[layhome/General script]
views.get("label3").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 57;BA.debugLine="Label3.Left = 0"[layhome/General script]
views.get("label3").vw.setLeft((int)(0d));
//BA.debugLineNum = 58;BA.debugLine="Label3.Top = 76%y"[layhome/General script]
views.get("label3").vw.setTop((int)((76d / 100 * height)));
//BA.debugLineNum = 60;BA.debugLine="Label4.Width = 95%x"[layhome/General script]
views.get("label4").vw.setWidth((int)((95d / 100 * width)));
//BA.debugLineNum = 61;BA.debugLine="Label4.Height = 8%y"[layhome/General script]
views.get("label4").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 62;BA.debugLine="Label4.Left = 0"[layhome/General script]
views.get("label4").vw.setLeft((int)(0d));
//BA.debugLineNum = 63;BA.debugLine="Label4.Top = 84%y"[layhome/General script]
views.get("label4").vw.setTop((int)((84d / 100 * height)));
//BA.debugLineNum = 65;BA.debugLine="Label5.Width = 95%x"[layhome/General script]
views.get("label5").vw.setWidth((int)((95d / 100 * width)));
//BA.debugLineNum = 66;BA.debugLine="Label5.Height = 8%y"[layhome/General script]
views.get("label5").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 67;BA.debugLine="Label5.Left = 0"[layhome/General script]
views.get("label5").vw.setLeft((int)(0d));
//BA.debugLineNum = 68;BA.debugLine="Label5.Top = 92%y"[layhome/General script]
views.get("label5").vw.setTop((int)((92d / 100 * height)));
//BA.debugLineNum = 70;BA.debugLine="icon1.Width = 14%x"[layhome/General script]
views.get("icon1").vw.setWidth((int)((14d / 100 * width)));
//BA.debugLineNum = 71;BA.debugLine="icon1.Height = 14%x"[layhome/General script]
views.get("icon1").vw.setHeight((int)((14d / 100 * width)));
//BA.debugLineNum = 72;BA.debugLine="icon1.Left = 1dip"[layhome/General script]
views.get("icon1").vw.setLeft((int)((1d * scale)));
//BA.debugLineNum = 73;BA.debugLine="icon1.Top = 1dip"[layhome/General script]
views.get("icon1").vw.setTop((int)((1d * scale)));
//BA.debugLineNum = 75;BA.debugLine="icon2.Width = 14%x"[layhome/General script]
views.get("icon2").vw.setWidth((int)((14d / 100 * width)));
//BA.debugLineNum = 76;BA.debugLine="icon2.Height = 14%x"[layhome/General script]
views.get("icon2").vw.setHeight((int)((14d / 100 * width)));
//BA.debugLineNum = 77;BA.debugLine="icon2.Left = 86%x-1dip"[layhome/General script]
views.get("icon2").vw.setLeft((int)((86d / 100 * width)-(1d * scale)));
//BA.debugLineNum = 78;BA.debugLine="icon2.Top = 1dip"[layhome/General script]
views.get("icon2").vw.setTop((int)((1d * scale)));
//BA.debugLineNum = 80;BA.debugLine="button1.Width = 100%x"[layhome/General script]
views.get("button1").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 81;BA.debugLine="button1.Height = 30%x"[layhome/General script]
views.get("button1").vw.setHeight((int)((30d / 100 * width)));
//BA.debugLineNum = 82;BA.debugLine="button1.Left = 0"[layhome/General script]
views.get("button1").vw.setLeft((int)(0d));
//BA.debugLineNum = 83;BA.debugLine="button1.Top = 0"[layhome/General script]
views.get("button1").vw.setTop((int)(0d));
//BA.debugLineNum = 85;BA.debugLine="imgThumb.Width = 20%y"[layhome/General script]
views.get("imgthumb").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 86;BA.debugLine="imgThumb.Height = 20%y"[layhome/General script]
views.get("imgthumb").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 87;BA.debugLine="imgThumb.Left = 50%x-20%y"[layhome/General script]
views.get("imgthumb").vw.setLeft((int)((50d / 100 * width)-(20d / 100 * height)));
//BA.debugLineNum = 88;BA.debugLine="imgThumb.Top = 30%x"[layhome/General script]
views.get("imgthumb").vw.setTop((int)((30d / 100 * width)));
//BA.debugLineNum = 90;BA.debugLine="imgDad.Width = 20%y"[layhome/General script]
views.get("imgdad").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 91;BA.debugLine="imgDad.Height = 20%y"[layhome/General script]
views.get("imgdad").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 92;BA.debugLine="imgDad.Left = 50%x"[layhome/General script]
views.get("imgdad").vw.setLeft((int)((50d / 100 * width)));
//BA.debugLineNum = 93;BA.debugLine="imgDad.Top = 30%x"[layhome/General script]
views.get("imgdad").vw.setTop((int)((30d / 100 * width)));

}
}