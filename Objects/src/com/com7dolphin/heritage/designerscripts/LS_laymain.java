package com.com7dolphin.heritage.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymain{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imageview1").vw.setLeft((int)(0d));
views.get("imageview1").vw.setTop((int)(0d));
views.get("imageview1").vw.setWidth((int)((50d / 100 * width)));
views.get("imageview1").vw.setHeight((int)((20d / 100 * height)));
views.get("imageview2").vw.setLeft((int)(0d));
views.get("imageview2").vw.setTop((int)((80d / 100 * height)));
views.get("imageview2").vw.setWidth((int)((50d / 100 * width)));
views.get("imageview2").vw.setHeight((int)((20d / 100 * height)));
views.get("toppanel").vw.setLeft((int)(0d));
views.get("toppanel").vw.setTop((int)(0-(50d / 100 * height)));
views.get("toppanel").vw.setWidth((int)((100d / 100 * width)));
views.get("toppanel").vw.setHeight((int)((50d / 100 * height)));
views.get("mainpanel").vw.setLeft((int)(0d));
views.get("mainpanel").vw.setTop((int)(0d));
views.get("mainpanel").vw.setWidth((int)((100d / 100 * width)));
views.get("mainpanel").vw.setHeight((int)((100d / 100 * height)));
views.get("blurpanel").vw.setLeft((int)(0d));
views.get("blurpanel").vw.setTop((int)(0d));
views.get("blurpanel").vw.setWidth((int)((100d / 100 * width)));
views.get("blurpanel").vw.setHeight((int)((100d / 100 * height)));
views.get("rightmenu").vw.setLeft((int)((100d / 100 * width)));
views.get("rightmenu").vw.setTop((int)(0d));
views.get("rightmenu").vw.setWidth((int)((50d / 100 * width)));
views.get("rightmenu").vw.setHeight((int)((100d / 100 * height)));
views.get("toolslist").vw.setLeft((int)(0d));
views.get("toolslist").vw.setTop((int)((20d / 100 * height)));
views.get("toolslist").vw.setWidth((int)((50d / 100 * width)));
views.get("toolslist").vw.setHeight((int)((90d / 100 * height)));
views.get("topimg").vw.setLeft((int)(0d));
views.get("topimg").vw.setTop((int)(0d));
views.get("topimg").vw.setWidth((int)((100d / 100 * width)));
views.get("topimg").vw.setHeight((int)((10d / 100 * height)));
views.get("homeimg").vw.setLeft((int)((1d / 100 * width)));
views.get("homeimg").vw.setTop((int)((1d / 100 * height)));
views.get("homeimg").vw.setWidth((int)((8d / 100 * height)));
views.get("homeimg").vw.setHeight((int)((8d / 100 * height)));
views.get("menuimg").vw.setLeft((int)((99d / 100 * width)-(8d / 100 * height)));
views.get("menuimg").vw.setTop((int)((1d / 100 * height)));
views.get("menuimg").vw.setWidth((int)((8d / 100 * height)));
views.get("menuimg").vw.setHeight((int)((8d / 100 * height)));
views.get("titlelabel").vw.setLeft((int)((25d / 100 * width)));
views.get("titlelabel").vw.setTop((int)((1d / 100 * height)));
views.get("titlelabel").vw.setWidth((int)((50d / 100 * width)));
views.get("titlelabel").vw.setHeight((int)((8d / 100 * height)));
views.get("webview1").vw.setLeft((int)(0d));
views.get("webview1").vw.setTop((int)((10d / 100 * height)));
views.get("webview1").vw.setWidth((int)((100d / 100 * width)));
views.get("webview1").vw.setHeight((int)((90d / 100 * height)));

}
}