package com.com7dolphin.heritage;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.com7dolphin.heritage", "com.com7dolphin.heritage.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.com7dolphin.heritage", "com.com7dolphin.heritage.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.com7dolphin.heritage.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static String _vvvvvvvvvvvvvvv5 = "";
public static String _vvvvvvvvvvvvvvv6 = "";
public static anywheresoftware.b4a.objects.Timer _vvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _welcomepanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _homepanel = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _button1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _button2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _button3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _button4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _button5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgthumb = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdad = null;
public com.com7dolphin.heritage.act2 _vvvvvvvvvvvvvvvvvv3 = null;
public com.com7dolphin.heritage.dbutils _vvvvvvvvvvvvvvvvvv4 = null;
public com.com7dolphin.heritage.act3 _vvvvvvvvvvvvvvvvvv5 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (act2.mostCurrent != null);
vis = vis | (act3.mostCurrent != null);
return vis;}
public static String  _a_animationend() throws Exception{
 //BA.debugLineNum = 177;BA.debugLine="Sub a_AnimationEnd";
 //BA.debugLineNum = 178;BA.debugLine="If Sender = a1 Then";
if ((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vvvvvvvvvvvvvvv0.getObject()))) { 
 //BA.debugLineNum = 179;BA.debugLine="pageTitle = \"ارث من\"";
_vvvvvvvvvvvvvvv6 = "ارث من";
 //BA.debugLineNum = 180;BA.debugLine="StartActivity(act3)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv5.getObject()));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vvvvvvvvvvvvvvvv1.getObject()))) { 
 //BA.debugLineNum = 182;BA.debugLine="PageURL = \"/data/will.html#menu\"";
_vvvvvvvvvvvvvvv5 = "/data/will.html#menu";
 //BA.debugLineNum = 183;BA.debugLine="pageTitle = \"وصیت من\"";
_vvvvvvvvvvvvvvv6 = "وصیت من";
 //BA.debugLineNum = 184;BA.debugLine="StartActivity(Act2)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv3.getObject()));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vvvvvvvvvvvvvvvv2.getObject()))) { 
 //BA.debugLineNum = 186;BA.debugLine="PageURL = \"/data/maliat.html#menu\"";
_vvvvvvvvvvvvvvv5 = "/data/maliat.html#menu";
 //BA.debugLineNum = 187;BA.debugLine="pageTitle = \"مالیات بر ارث\"";
_vvvvvvvvvvvvvvv6 = "مالیات بر ارث";
 //BA.debugLineNum = 188;BA.debugLine="StartActivity(Act2)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv3.getObject()));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vvvvvvvvvvvvvvvv3.getObject()))) { 
 //BA.debugLineNum = 190;BA.debugLine="PageURL = \"/data/law.html#menu\"";
_vvvvvvvvvvvvvvv5 = "/data/law.html#menu";
 //BA.debugLineNum = 191;BA.debugLine="pageTitle = \"قانون ارث\"";
_vvvvvvvvvvvvvvv6 = "قانون ارث";
 //BA.debugLineNum = 192;BA.debugLine="StartActivity(Act2)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv3.getObject()));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vvvvvvvvvvvvvvvv4.getObject()))) { 
 //BA.debugLineNum = 194;BA.debugLine="PageURL = \"/data/dict.html#menu\"";
_vvvvvvvvvvvvvvv5 = "/data/dict.html#menu";
 //BA.debugLineNum = 195;BA.debugLine="pageTitle = \"لغت نامه\"";
_vvvvvvvvvvvvvvv6 = "لغت نامه";
 //BA.debugLineNum = 196;BA.debugLine="StartActivity(Act2)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv3.getObject()));
 };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="Activity.LoadLayout(\"layHome\")";
mostCurrent._activity.LoadLayout("layHome",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="Dim myFont As Typeface";
_myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 66;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
_myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 67;BA.debugLine="SetTypeface(homePanel,myFont)";
_vvvvvvvvvvvvvvvvvvvvv4(mostCurrent._homepanel,_myfont);
 //BA.debugLineNum = 69;BA.debugLine="showWelcome(2500)";
_vvvvvvvvvvvvvvvvvvvvv5((int) (2500));
 //BA.debugLineNum = 71;BA.debugLine="a1.InitializeTranslate(\"a\", 0, 0, 0, 4)";
mostCurrent._vvvvvvvvvvvvvvv0.InitializeTranslate(mostCurrent.activityBA,"a",(float) (0),(float) (0),(float) (0),(float) (4));
 //BA.debugLineNum = 72;BA.debugLine="a1.RepeatMode = a1.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvv0.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvv0.REPEAT_REVERSE);
 //BA.debugLineNum = 73;BA.debugLine="a1.Duration = 200";
mostCurrent._vvvvvvvvvvvvvvv0.setDuration((long) (200));
 //BA.debugLineNum = 74;BA.debugLine="a2.InitializeTranslate(\"a\", 0, 0, 0, 4)";
mostCurrent._vvvvvvvvvvvvvvvv1.InitializeTranslate(mostCurrent.activityBA,"a",(float) (0),(float) (0),(float) (0),(float) (4));
 //BA.debugLineNum = 75;BA.debugLine="a2.RepeatMode = a2.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvvv1.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvvv1.REPEAT_REVERSE);
 //BA.debugLineNum = 76;BA.debugLine="a2.Duration = 200";
mostCurrent._vvvvvvvvvvvvvvvv1.setDuration((long) (200));
 //BA.debugLineNum = 77;BA.debugLine="a3.InitializeTranslate(\"a\", 0, 0, 0, 4)";
mostCurrent._vvvvvvvvvvvvvvvv2.InitializeTranslate(mostCurrent.activityBA,"a",(float) (0),(float) (0),(float) (0),(float) (4));
 //BA.debugLineNum = 78;BA.debugLine="a3.RepeatMode = a3.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvvv2.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvvv2.REPEAT_REVERSE);
 //BA.debugLineNum = 79;BA.debugLine="a3.Duration = 200";
mostCurrent._vvvvvvvvvvvvvvvv2.setDuration((long) (200));
 //BA.debugLineNum = 80;BA.debugLine="a4.InitializeTranslate(\"a\", 0, 0, 0, 4)";
mostCurrent._vvvvvvvvvvvvvvvv3.InitializeTranslate(mostCurrent.activityBA,"a",(float) (0),(float) (0),(float) (0),(float) (4));
 //BA.debugLineNum = 81;BA.debugLine="a4.RepeatMode = a4.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvvv3.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvvv3.REPEAT_REVERSE);
 //BA.debugLineNum = 82;BA.debugLine="a4.Duration = 200";
mostCurrent._vvvvvvvvvvvvvvvv3.setDuration((long) (200));
 //BA.debugLineNum = 83;BA.debugLine="a5.InitializeTranslate(\"a\", 0, 0, 0, 4)";
mostCurrent._vvvvvvvvvvvvvvvv4.InitializeTranslate(mostCurrent.activityBA,"a",(float) (0),(float) (0),(float) (0),(float) (4));
 //BA.debugLineNum = 84;BA.debugLine="a5.RepeatMode = a5.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvvv4.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvvv4.REPEAT_REVERSE);
 //BA.debugLineNum = 85;BA.debugLine="a5.Duration = 200";
mostCurrent._vvvvvvvvvvvvvvvv4.setDuration((long) (200));
 //BA.debugLineNum = 86;BA.debugLine="thumb.InitializeTranslate(\"thumb\", 0, 0, 0, -10)";
mostCurrent._vvvvvvvvvvvvvvvv5.InitializeTranslate(mostCurrent.activityBA,"thumb",(float) (0),(float) (0),(float) (0),(float) (-10));
 //BA.debugLineNum = 87;BA.debugLine="thumb.RepeatMode = a5.REPEAT_REVERSE";
mostCurrent._vvvvvvvvvvvvvvvv5.setRepeatMode(mostCurrent._vvvvvvvvvvvvvvvv4.REPEAT_REVERSE);
 //BA.debugLineNum = 88;BA.debugLine="thumb.Duration = 700";
mostCurrent._vvvvvvvvvvvvvvvv5.setDuration((long) (700));
 //BA.debugLineNum = 89;BA.debugLine="thumb.RepeatCount = 3";
mostCurrent._vvvvvvvvvvvvvvvv5.setRepeatCount((int) (3));
 //BA.debugLineNum = 90;BA.debugLine="thumbOut.InitializeAlpha(\"thumbOut\",1,0)";
mostCurrent._vvvvvvvvvvvvvvvv6.InitializeAlpha(mostCurrent.activityBA,"thumbOut",(float) (1),(float) (0));
 //BA.debugLineNum = 91;BA.debugLine="thumbOut.Duration = 700";
mostCurrent._vvvvvvvvvvvvvvvv6.setDuration((long) (700));
 //BA.debugLineNum = 92;BA.debugLine="thumbIn.InitializeAlpha(\"thumbIn\",0,1)";
mostCurrent._vvvvvvvvvvvvvvvv7.InitializeAlpha(mostCurrent.activityBA,"thumbIn",(float) (0),(float) (1));
 //BA.debugLineNum = 93;BA.debugLine="thumbIn.Duration = 700";
mostCurrent._vvvvvvvvvvvvvvvv7.setDuration((long) (700));
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 144;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 145;BA.debugLine="If Msgbox2(\"آیا میخواهید از برنامه خارج شوید؟\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("آیا میخواهید از برنامه خارج شوید؟","توجه","بلی","","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 146;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 148;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub button1_Click";
 //BA.debugLineNum = 154;BA.debugLine="a1.Start(button1)";
mostCurrent._vvvvvvvvvvvvvvv0.Start((android.view.View)(mostCurrent._button1.getObject()));
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub button2_Click";
 //BA.debugLineNum = 158;BA.debugLine="a2.Start(button2)";
mostCurrent._vvvvvvvvvvvvvvvv1.Start((android.view.View)(mostCurrent._button2.getObject()));
 //BA.debugLineNum = 159;BA.debugLine="a2.Start(Label2)";
mostCurrent._vvvvvvvvvvvvvvvv1.Start((android.view.View)(mostCurrent._label2.getObject()));
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub button3_Click";
 //BA.debugLineNum = 163;BA.debugLine="a3.Start(button3)";
mostCurrent._vvvvvvvvvvvvvvvv2.Start((android.view.View)(mostCurrent._button3.getObject()));
 //BA.debugLineNum = 164;BA.debugLine="a3.Start(Label3)";
mostCurrent._vvvvvvvvvvvvvvvv2.Start((android.view.View)(mostCurrent._label3.getObject()));
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub button4_Click";
 //BA.debugLineNum = 168;BA.debugLine="a4.Start(button4)";
mostCurrent._vvvvvvvvvvvvvvvv3.Start((android.view.View)(mostCurrent._button4.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="a4.Start(Label4)";
mostCurrent._vvvvvvvvvvvvvvvv3.Start((android.view.View)(mostCurrent._label4.getObject()));
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Sub button5_Click";
 //BA.debugLineNum = 173;BA.debugLine="a5.Start(button5)";
mostCurrent._vvvvvvvvvvvvvvvv4.Start((android.view.View)(mostCurrent._button5.getObject()));
 //BA.debugLineNum = 174;BA.debugLine="a5.Start(Label5)";
mostCurrent._vvvvvvvvvvvvvvvv4.Start((android.view.View)(mostCurrent._label5.getObject()));
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv6(anywheresoftware.b4a.objects.PanelWrapper _p,boolean _enabled) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub EnableAll(p As Panel, Enabled As Boolean)";
 //BA.debugLineNum = 134;BA.debugLine="For Each v As View In p";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group90 = _p;
final int groupLen90 = group90.getSize();
for (int index90 = 0;index90 < groupLen90 ;index90++){
_v.setObject((android.view.View)(group90.Get(index90)));
 //BA.debugLineNum = 135;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 136;BA.debugLine="EnableAll(v, Enabled)";
_vvvvvvvvvvvvvvvvvvvvv6((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_enabled);
 }else {
 //BA.debugLineNum = 138;BA.debugLine="v.Enabled = Enabled";
_v.setEnabled(_enabled);
 };
 }
;
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 34;BA.debugLine="Dim a1,a2,a3,a4,a5,thumb,thumbOut,thumbIn As Anim";
mostCurrent._vvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim welcomePanel As Panel";
mostCurrent._welcomepanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim homePanel As Panel";
mostCurrent._homepanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim button1 As ImageView";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim button2 As ImageView";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim button3 As ImageView";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim button4 As ImageView";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim button5 As ImageView";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private imgThumb As ImageView";
mostCurrent._imgthumb = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private imgDad As ImageView";
mostCurrent._imgdad = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _icon1_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _share1 = null;
 //BA.debugLineNum = 200;BA.debugLine="Sub icon1_Click";
 //BA.debugLineNum = 202;BA.debugLine="Dim share1 As Intent";
_share1 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 203;BA.debugLine="share1.Initialize(share1.ACTION_SEND,\"\")";
_share1.Initialize(_share1.ACTION_SEND,"");
 //BA.debugLineNum = 204;BA.debugLine="share1.SetType(\"text/plain\")";
_share1.SetType("text/plain");
 //BA.debugLineNum = 205;BA.debugLine="share1.PutExtra(\"android.intent.extra.TEXT\", \"ht";
_share1.PutExtra("android.intent.extra.TEXT",(Object)("http://cafebazaar.ir/app/com.com7dolphin.heritage/"));
 //BA.debugLineNum = 206;BA.debugLine="share1.WrapAsIntentChooser(\"Share via\")";
_share1.WrapAsIntentChooser("Share via");
 //BA.debugLineNum = 207;BA.debugLine="StartActivity(share1)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_share1.getObject()));
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _icon2_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 210;BA.debugLine="Sub icon2_Click";
 //BA.debugLineNum = 211;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 212;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.7dolph";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://www.7dolphin.com")));
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
act2._process_globals();
dbutils._process_globals();
act3._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 29;BA.debugLine="Dim PageURL, pageTitle As String";
_vvvvvvvvvvvvvvv5 = "";
_vvvvvvvvvvvvvvv6 = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim welcomeTimer As Timer";
_vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv4(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 51;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 52;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group22 = _parent;
final int groupLen22 = group22.getSize();
for (int index22 = 0;index22 < groupLen22 ;index22++){
_v.setObject((android.view.View)(group22.Get(index22)));
 //BA.debugLineNum = 53;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 54;BA.debugLine="SetTypeface(v, t)";
_vvvvvvvvvvvvvvvvvvvvv4((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 56;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 57;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
;
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv5(int _duration) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub showWelcome(duration As Int)";
 //BA.debugLineNum = 97;BA.debugLine="EnableAll(homePanel,False)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._homepanel,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 98;BA.debugLine="welcomeTimer.Initialize(\"welTimer\",duration)";
_vvvvvvvvvvvvvvv7.Initialize(processBA,"welTimer",(long) (_duration));
 //BA.debugLineNum = 99;BA.debugLine="welcomeTimer.Enabled = True";
_vvvvvvvvvvvvvvv7.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _thumb_animationend() throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Sub thumb_AnimationEnd";
 //BA.debugLineNum = 116;BA.debugLine="thumbOut.Start(imgDad)";
mostCurrent._vvvvvvvvvvvvvvvv6.Start((android.view.View)(mostCurrent._imgdad.getObject()));
 //BA.debugLineNum = 117;BA.debugLine="thumbOut.Start(imgThumb)";
mostCurrent._vvvvvvvvvvvvvvvv6.Start((android.view.View)(mostCurrent._imgthumb.getObject()));
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _thumbin_animationend() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub thumbIn_AnimationEnd";
 //BA.debugLineNum = 112;BA.debugLine="thumb.Start(imgThumb)";
mostCurrent._vvvvvvvvvvvvvvvv5.Start((android.view.View)(mostCurrent._imgthumb.getObject()));
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _thumbout_animationend() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub thumbOut_AnimationEnd";
 //BA.debugLineNum = 121;BA.debugLine="imgDad.Visible = False";
mostCurrent._imgdad.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 122;BA.debugLine="imgThumb.Visible = False";
mostCurrent._imgthumb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _weltimer_tick() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub welTimer_tick";
 //BA.debugLineNum = 103;BA.debugLine="welcomeTimer.Enabled = False";
_vvvvvvvvvvvvvvv7.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="welcomePanel.Visible = False";
mostCurrent._welcomepanel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="homePanel.Visible = True";
mostCurrent._homepanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 106;BA.debugLine="EnableAll(homePanel,True)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._homepanel,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="thumbIn.Start(imgThumb)";
mostCurrent._vvvvvvvvvvvvvvvv7.Start((android.view.View)(mostCurrent._imgthumb.getObject()));
 //BA.debugLineNum = 108;BA.debugLine="thumbIn.Start(imgDad)";
mostCurrent._vvvvvvvvvvvvvvvv7.Start((android.view.View)(mostCurrent._imgdad.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
}
