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

public class act2 extends Activity implements B4AActivity{
	public static act2 mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.com7dolphin.heritage", "com.com7dolphin.heritage.act2");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (act2).");
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
		activityBA = new BA(this, layout, processBA, "com.com7dolphin.heritage", "com.com7dolphin.heritage.act2");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.com7dolphin.heritage.act2", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (act2) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (act2) Resume **");
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
		return act2.class;
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
        BA.LogInfo("** Activity (act2) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (act2) Resume **");
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
public static anywheresoftware.b4a.sql.SQL _v5 = null;
public static String _v6 = "";
public anywheresoftware.b4a.sql.SQL.CursorWrapper _v7 = null;
public Object _v0 = null;
public com.com7dolphin.heritage.searchview _vv1 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv2 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv3 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv4 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv5 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv6 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vv7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _rightmenu = null;
public anywheresoftware.b4a.objects.PanelWrapper _mainpanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _blurpanel = null;
public static boolean _vv0 = false;
public static boolean _vvv1 = false;
public anywheresoftware.b4a.objects.ListViewWrapper _toolslist = null;
public uk.co.martinpearman.b4a.webviewextras.WebViewExtras _vvv2 = null;
public static String _vvv3 = "";
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _vvv4 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _titlelabel = null;
public anywheresoftware.b4a.objects.PanelWrapper _toppanel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _vvv5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvv6 = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2 _vvv7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvv0 = null;
public static String _vvvv1 = "";
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _vvvv2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _vvvv3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _vvvv4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _vvvv5 = null;
public com.com7dolphin.heritage.main _vvvvvvvvvvvvvvvvvv2 = null;
public com.com7dolphin.heritage.dbutils _vvvvvvvvvvvvvvvvvv4 = null;
public com.com7dolphin.heritage.act3 _vvvvvvvvvvvvvvvvvv5 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Create (FirstTime As Boolean)";
 //BA.debugLineNum = 72;BA.debugLine="dir=DBUtils.CopyDBFromAssets(\"heritage.db\")";
_v6 = mostCurrent._vvvvvvvvvvvvvvvvvv4._vvvv7(mostCurrent.activityBA,"heritage.db");
 //BA.debugLineNum = 73;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 74;BA.debugLine="SQL1.Initialize(dir, \"heritage.db\", False)";
_v5.Initialize(_v6,"heritage.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 77;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="titleLabel.Text = Main.pageTitle";
mostCurrent._titlelabel.setText((Object)(mostCurrent._vvvvvvvvvvvvvvvvvv2._vvvvvvvvvvvvvvv6));
 //BA.debugLineNum = 79;BA.debugLine="WebView1.LoadUrl(\"file:///android_asset\" & Main.p";
mostCurrent._webview1.LoadUrl("file:///android_asset"+mostCurrent._vvvvvvvvvvvvvvvvvv2._vvvvvvvvvvvvvvv5);
 //BA.debugLineNum = 80;BA.debugLine="createMenu";
_vvvvvvvvvvvvvvvvvvvvv7();
 //BA.debugLineNum = 81;BA.debugLine="createFavList";
_vvvvvvvvvvvvvvvvvvvvv0();
 //BA.debugLineNum = 82;BA.debugLine="createFont";
_vvvvvvvvvvvvvvvvvvvvvv1();
 //BA.debugLineNum = 83;BA.debugLine="createSerch";
_vvvvvvvvvvvvvvvvvvvvvv2();
 //BA.debugLineNum = 85;BA.debugLine="Dim myFont As Typeface";
_myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 86;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
_myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 87;BA.debugLine="SetTypeface(mainPanel,myFont)";
_vvvvvvvvvvvvvvvvvvvvv4(mostCurrent._mainpanel,_myfont);
 //BA.debugLineNum = 88;BA.debugLine="SetTypeface(rightMenu,myFont)";
_vvvvvvvvvvvvvvvvvvvvv4(mostCurrent._rightmenu,_myfont);
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 258;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 259;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 260;BA.debugLine="If rightMenuIsOpen = True Then";
if (_vv0==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 261;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 262;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if(_vvv1==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 264;BA.debugLine="closeTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv4();
 //BA.debugLineNum = 265;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if((mostCurrent._webview1.getUrl().substring(mostCurrent._webview1.getUrl().indexOf("#"))).equals("#menu") == false) { 
 //BA.debugLineNum = 267;BA.debugLine="Javascript=\"document.location.href = '#menu'\"";
mostCurrent._vvv3 = "document.location.href = '#menu'";
 //BA.debugLineNum = 268;BA.debugLine="MyWebViewExtras.executeJavascript(WebView1, Jav";
mostCurrent._vvv2.executeJavascript((android.webkit.WebView)(mostCurrent._webview1.getObject()),mostCurrent._vvv3);
 //BA.debugLineNum = 269;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _blurpanel_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 204;BA.debugLine="Sub blurPanel_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 205;BA.debugLine="If Action = Activity.ACTION_DOWN Then";
if (_action==mostCurrent._activity.ACTION_DOWN) { 
 //BA.debugLineNum = 206;BA.debugLine="If rightMenuIsOpen Then";
if (_vv0) { 
 //BA.debugLineNum = 207;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 }else if(_vvv1) { 
 //BA.debugLineNum = 209;BA.debugLine="closeTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv4();
 };
 };
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv5() throws Exception{
String _str = "";
int _i = 0;
 //BA.debugLineNum = 292;BA.debugLine="Sub buildFav";
 //BA.debugLineNum = 293;BA.debugLine="Dim str As String";
_str = "";
 //BA.debugLineNum = 294;BA.debugLine="favList.Clear";
mostCurrent._vvv5.Clear();
 //BA.debugLineNum = 295;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT * FROM favs\")";
mostCurrent._v7.setObject((android.database.Cursor)(_v5.ExecQuery("SELECT * FROM favs")));
 //BA.debugLineNum = 296;BA.debugLine="bitmap1.Initialize(File.DirAssets, \"fav.png\")";
mostCurrent._vvv4.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fav.png");
 //BA.debugLineNum = 297;BA.debugLine="For i = 0 To Cursor1.RowCount - 1";
{
final int step238 = 1;
final int limit238 = (int) (mostCurrent._v7.getRowCount()-1);
for (_i = (int) (0); (step238 > 0 && _i <= limit238) || (step238 < 0 && _i >= limit238); _i = ((int)(0 + _i + step238))) {
 //BA.debugLineNum = 298;BA.debugLine="Cursor1.Position = i";
mostCurrent._v7.setPosition(_i);
 //BA.debugLineNum = 299;BA.debugLine="str = Cursor1.GetString(\"favinfo\")";
_str = mostCurrent._v7.GetString("favinfo");
 //BA.debugLineNum = 300;BA.debugLine="favList.AddTwoLinesAndBitmap2(str.SubString2(0,s";
mostCurrent._vvv5.AddTwoLinesAndBitmap2(_str.substring((int) (0),_str.indexOf("-"))+": "+_str.substring((int) (_str.indexOf("#")+1)),"",(android.graphics.Bitmap)(mostCurrent._vvv4.getObject()),(Object)(_str));
 }
};
 //BA.debugLineNum = 302;BA.debugLine="Cursor1.Close";
mostCurrent._v7.Close();
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
public static String  _closeblurpanelanim_animationend() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub CloseBlurPanelAnim_AnimationEnd";
 //BA.debugLineNum = 177;BA.debugLine="blurPanel.Visible = False";
mostCurrent._blurpanel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv3() throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Sub closeRightMenu";
 //BA.debugLineNum = 215;BA.debugLine="rightMenuIsOpen = False";
_vv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 216;BA.debugLine="EnableAll(mainPanel, True)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._mainpanel,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 217;BA.debugLine="anCloseBlurPanel.Start(blurPanel)";
mostCurrent._vv5.Start((android.view.View)(mostCurrent._blurpanel.getObject()));
 //BA.debugLineNum = 218;BA.debugLine="anCloseRightMenu.Start(rightMenu)";
mostCurrent._vv3.Start((android.view.View)(mostCurrent._rightmenu.getObject()));
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv4() throws Exception{
 //BA.debugLineNum = 229;BA.debugLine="Sub closeTopPanel";
 //BA.debugLineNum = 230;BA.debugLine="topPanelIsOpen = False";
_vvv1 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 231;BA.debugLine="EnableAll(mainPanel, True)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._mainpanel,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 232;BA.debugLine="anCloseBlurPanel.Start(blurPanel)";
mostCurrent._vv5.Start((android.view.View)(mostCurrent._blurpanel.getObject()));
 //BA.debugLineNum = 233;BA.debugLine="anCloseTopPanel.Start(topPanel)";
mostCurrent._vv7.Start((android.view.View)(mostCurrent._toppanel.getObject()));
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv0() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub createFavList";
 //BA.debugLineNum = 124;BA.debugLine="favTitle.Initialize(\"favTitle\")";
mostCurrent._vvv6.Initialize(mostCurrent.activityBA,"favTitle");
 //BA.debugLineNum = 125;BA.debugLine="favTitle.Text=\"جهت حذف از کلیک مدت دار استفاده کن";
mostCurrent._vvv6.setText((Object)("جهت حذف از کلیک مدت دار استفاده کنید"));
 //BA.debugLineNum = 126;BA.debugLine="favTitle.TextColor = Colors.DarkGray";
mostCurrent._vvv6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 127;BA.debugLine="favTitle.TextSize = 15";
mostCurrent._vvv6.setTextSize((float) (15));
 //BA.debugLineNum = 128;BA.debugLine="favList.Initialize(\"favList\")";
mostCurrent._vvv5.Initialize(mostCurrent.activityBA,"favList");
 //BA.debugLineNum = 129;BA.debugLine="favList.TwoLinesAndBitmap.ImageView.SetLayout(84%";
mostCurrent._vvv5.getTwoLinesAndBitmap().ImageView.SetLayout(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (84),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 130;BA.debugLine="favList.TwoLinesAndBitmap.ItemHeight = 17%x";
mostCurrent._vvv5.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (17),mostCurrent.activityBA));
 //BA.debugLineNum = 131;BA.debugLine="favList.TwoLinesAndBitmap.Label.TextColor = Color";
mostCurrent._vvv5.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 132;BA.debugLine="favList.TwoLinesAndBitmap.Label.Gravity = Gravity";
mostCurrent._vvv5.getTwoLinesAndBitmap().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 133;BA.debugLine="favList.TwoLinesAndBitmap.Label.SetLayout(0, 4%x,";
mostCurrent._vvv5.getTwoLinesAndBitmap().Label.SetLayout((int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (4),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 134;BA.debugLine="favList.Color = Colors.Transparent";
mostCurrent._vvv5.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv1() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub createFont";
 //BA.debugLineNum = 92;BA.debugLine="pnl.Initialize(\"pnl\")";
mostCurrent._vvv0.Initialize(mostCurrent.activityBA,"pnl");
 //BA.debugLineNum = 93;BA.debugLine="pnl.Color = Colors.Transparent";
mostCurrent._vvv0.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 94;BA.debugLine="s1.Initialize(\"s\")";
mostCurrent._vvvv2.Initialize(mostCurrent.activityBA,"s");
 //BA.debugLineNum = 95;BA.debugLine="s2.Initialize(\"s\")";
mostCurrent._vvvv3.Initialize(mostCurrent.activityBA,"s");
 //BA.debugLineNum = 96;BA.debugLine="s3.Initialize(\"s\")";
mostCurrent._vvvv4.Initialize(mostCurrent.activityBA,"s");
 //BA.debugLineNum = 97;BA.debugLine="s4.Initialize(\"s\")";
mostCurrent._vvvv5.Initialize(mostCurrent.activityBA,"s");
 //BA.debugLineNum = 98;BA.debugLine="s1.Text = \"کوچک\"";
mostCurrent._vvvv2.setText((Object)("کوچک"));
 //BA.debugLineNum = 99;BA.debugLine="s2.Text = \"متوسط\"";
mostCurrent._vvvv3.setText((Object)("متوسط"));
 //BA.debugLineNum = 100;BA.debugLine="s3.Text = \"بزرگ\"";
mostCurrent._vvvv4.setText((Object)("بزرگ"));
 //BA.debugLineNum = 101;BA.debugLine="s4.Text = \"خیلی بزرگ\"";
mostCurrent._vvvv5.setText((Object)("خیلی بزرگ"));
 //BA.debugLineNum = 102;BA.debugLine="pnl.AddView(s1,0,0,50%x,10%y)";
mostCurrent._vvv0.AddView((android.view.View)(mostCurrent._vvvv2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 103;BA.debugLine="pnl.AddView(s2,0,10%y,50%x,10%y)";
mostCurrent._vvv0.AddView((android.view.View)(mostCurrent._vvvv3.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 104;BA.debugLine="pnl.AddView(s3,0,20%y,50%x,10%y)";
mostCurrent._vvv0.AddView((android.view.View)(mostCurrent._vvvv4.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 105;BA.debugLine="pnl.AddView(s4,0,30%y,50%x,10%y)";
mostCurrent._vvv0.AddView((android.view.View)(mostCurrent._vvvv5.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 106;BA.debugLine="cd.AddView(pnl, 55%x, 40%y)";
mostCurrent._vvv7.AddView((android.view.View)(mostCurrent._vvv0.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (55),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv7() throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub createMenu";
 //BA.debugLineNum = 142;BA.debugLine="anOpenRightMenu.InitializeTranslate(\"RightMenuAni";
mostCurrent._vv2.InitializeTranslate(mostCurrent.activityBA,"RightMenuAnim",(float) (0),(float) (0),(float) (-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 143;BA.debugLine="anOpenRightMenu.Duration = 300";
mostCurrent._vv2.setDuration((long) (300));
 //BA.debugLineNum = 144;BA.debugLine="anCloseRightMenu.InitializeTranslate(\"RightMenuAn";
mostCurrent._vv3.InitializeTranslate(mostCurrent.activityBA,"RightMenuAnim",(float) (0),(float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)),(float) (0));
 //BA.debugLineNum = 145;BA.debugLine="anCloseRightMenu.Duration = 300";
mostCurrent._vv3.setDuration((long) (300));
 //BA.debugLineNum = 146;BA.debugLine="anOpenBlurPanel.InitializeAlpha(\"OpenBlurPanelAni";
mostCurrent._vv4.InitializeAlpha(mostCurrent.activityBA,"OpenBlurPanelAnim",(float) (0),(float) (1));
 //BA.debugLineNum = 147;BA.debugLine="anOpenBlurPanel.Duration = 300";
mostCurrent._vv4.setDuration((long) (300));
 //BA.debugLineNum = 148;BA.debugLine="anCloseBlurPanel.InitializeAlpha(\"CloseBlurPanelA";
mostCurrent._vv5.InitializeAlpha(mostCurrent.activityBA,"CloseBlurPanelAnim",(float) (1),(float) (0));
 //BA.debugLineNum = 149;BA.debugLine="anCloseBlurPanel.Duration = 300";
mostCurrent._vv5.setDuration((long) (300));
 //BA.debugLineNum = 150;BA.debugLine="anOpenTopPanel.InitializeTranslate(\"TopPanelAnim\"";
mostCurrent._vv6.InitializeTranslate(mostCurrent.activityBA,"TopPanelAnim",(float) (0),(float) (0),(float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)));
 //BA.debugLineNum = 151;BA.debugLine="anOpenTopPanel.Duration = 300";
mostCurrent._vv6.setDuration((long) (300));
 //BA.debugLineNum = 152;BA.debugLine="anCloseTopPanel.InitializeTranslate(\"TopPanelAnim";
mostCurrent._vv7.InitializeTranslate(mostCurrent.activityBA,"TopPanelAnim",(float) (0),(float) (0),(float) (0),(float) (-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)));
 //BA.debugLineNum = 153;BA.debugLine="anCloseTopPanel.Duration = 300";
mostCurrent._vv7.setDuration((long) (300));
 //BA.debugLineNum = 154;BA.debugLine="rightMenuIsOpen = False";
_vv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 155;BA.debugLine="topPanelIsOpen = False";
_vvv1 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 157;BA.debugLine="toolsList.TwoLinesAndBitmap.ImageView.SetLayout(3";
mostCurrent._toolslist.getTwoLinesAndBitmap().ImageView.SetLayout(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (38),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12),mostCurrent.activityBA));
 //BA.debugLineNum = 158;BA.debugLine="toolsList.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._toolslist.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 159;BA.debugLine="toolsList.TwoLinesAndBitmap.ItemHeight = 15%x";
mostCurrent._toolslist.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 160;BA.debugLine="toolsList.TwoLinesAndBitmap.Label.TextColor = Col";
mostCurrent._toolslist.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 161;BA.debugLine="toolsList.TwoLinesAndBitmap.Label.Gravity = Gravi";
mostCurrent._toolslist.getTwoLinesAndBitmap().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 162;BA.debugLine="toolsList.TwoLinesAndBitmap.Label.SetLayout(5%x,";
mostCurrent._toolslist.getTwoLinesAndBitmap().Label.SetLayout(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (13),mostCurrent.activityBA));
 //BA.debugLineNum = 163;BA.debugLine="toolsList.TwoLinesAndBitmap.Label.TextSize = 18";
mostCurrent._toolslist.getTwoLinesAndBitmap().Label.setTextSize((float) (18));
 //BA.debugLineNum = 164;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"می پسندم\", \"\", L";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("می پسندم","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"addfav.png").getObject()),(Object)("addfav"));
 //BA.debugLineNum = 165;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"علاقه مندی ها\",";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("علاقه مندی ها","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fav.png").getObject()),(Object)("fav"));
 //BA.debugLineNum = 166;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"اندازه قلم\", \"\",";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("اندازه قلم","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"font.png").getObject()),(Object)("font"));
 //BA.debugLineNum = 167;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"اشتراک\", \"\", Loa";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("اشتراک","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"share.png").getObject()),(Object)("share"));
 //BA.debugLineNum = 168;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"جستجو\", \"\", Load";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("جستجو","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"search.png").getObject()),(Object)("search"));
 //BA.debugLineNum = 169;BA.debugLine="toolsList.AddTwoLinesAndBitmap2(\"درباره\", \"\", Loa";
mostCurrent._toolslist.AddTwoLinesAndBitmap2("درباره","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()),(Object)("about"));
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv2() throws Exception{
anywheresoftware.b4a.objects.collections.List _ind = null;
int _i = 0;
 //BA.debugLineNum = 109;BA.debugLine="Sub createSerch";
 //BA.debugLineNum = 110;BA.debugLine="sv.Initialize(Me, \"sv\")";
mostCurrent._vv1._initialize(mostCurrent.activityBA,act2.getObject(),"sv");
 //BA.debugLineNum = 111;BA.debugLine="Dim ind As List";
_ind = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 112;BA.debugLine="ind.Initialize";
_ind.Initialize();
 //BA.debugLineNum = 113;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT ind FROM search\"";
mostCurrent._v7.setObject((android.database.Cursor)(_v5.ExecQuery("SELECT ind FROM search")));
 //BA.debugLineNum = 114;BA.debugLine="For i = 0 To Cursor1.RowCount - 1";
{
final int step82 = 1;
final int limit82 = (int) (mostCurrent._v7.getRowCount()-1);
for (_i = (int) (0); (step82 > 0 && _i <= limit82) || (step82 < 0 && _i >= limit82); _i = ((int)(0 + _i + step82))) {
 //BA.debugLineNum = 115;BA.debugLine="Cursor1.Position = i";
mostCurrent._v7.setPosition(_i);
 //BA.debugLineNum = 116;BA.debugLine="ind.Add(Cursor1.GetString(\"ind\"))";
_ind.Add((Object)(mostCurrent._v7.GetString("ind")));
 }
};
 //BA.debugLineNum = 118;BA.debugLine="Cursor1.Close";
mostCurrent._v7.Close();
 //BA.debugLineNum = 119;BA.debugLine="index = sv.SetItems(ind)";
mostCurrent._v0 = mostCurrent._vv1._vvvvvvvvvvvvvvvvv3(_ind);
 //BA.debugLineNum = 120;BA.debugLine="sv.SetIndex(index)";
mostCurrent._vv1._vvvvvvvvvvvvvvvvv2(mostCurrent._v0);
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv6(anywheresoftware.b4a.objects.PanelWrapper _p,boolean _enabled) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 248;BA.debugLine="Sub EnableAll(p As Panel, Enabled As Boolean)";
 //BA.debugLineNum = 249;BA.debugLine="For Each v As View In p";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group194 = _p;
final int groupLen194 = group194.getSize();
for (int index194 = 0;index194 < groupLen194 ;index194++){
_v.setObject((android.view.View)(group194.Get(index194)));
 //BA.debugLineNum = 250;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 251;BA.debugLine="EnableAll(v, Enabled)";
_vvvvvvvvvvvvvvvvvvvvv6((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_enabled);
 }else {
 //BA.debugLineNum = 253;BA.debugLine="v.Enabled = Enabled";
_v.setEnabled(_enabled);
 };
 }
;
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _favlist_itemclick(int _position,Object _value) throws Exception{
String _str = "";
String _pageurl = "";
 //BA.debugLineNum = 274;BA.debugLine="Sub favList_ItemClick (Position As Int, Value As O";
 //BA.debugLineNum = 275;BA.debugLine="Dim str, pageURL As String";
_str = "";
_pageurl = "";
 //BA.debugLineNum = 276;BA.debugLine="str = favList.GetItem(Position)";
_str = BA.ObjectToString(mostCurrent._vvv5.GetItem(_position));
 //BA.debugLineNum = 277;BA.debugLine="titleLabel.Text = str.SubString2(0,str.IndexOf(\"-";
mostCurrent._titlelabel.setText((Object)(_str.substring((int) (0),_str.indexOf("-"))));
 //BA.debugLineNum = 278;BA.debugLine="pageURL = str.SubString(str.IndexOf(\"-\")+1)";
_pageurl = _str.substring((int) (_str.indexOf("-")+1));
 //BA.debugLineNum = 279;BA.debugLine="WebView1.LoadUrl(pageURL)";
mostCurrent._webview1.LoadUrl(_pageurl);
 //BA.debugLineNum = 280;BA.debugLine="closeTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv4();
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _favlist_itemlongclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Sub favList_ItemLongClick (Position As Int, Value";
 //BA.debugLineNum = 284;BA.debugLine="If Msgbox2(\"از لیست حذف شود؟\",\"توجه\", \"بلی\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("از لیست حذف شود؟","توجه","بلی","","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 285;BA.debugLine="SQL1.ExecNonQuery(\"delete from favs where favinf";
_v5.ExecNonQuery("delete from favs where favinfo = '"+BA.ObjectToString(mostCurrent._vvv5.GetItem(_position))+"'");
 //BA.debugLineNum = 286;BA.debugLine="buildFav";
_vvvvvvvvvvvvvvvvvvvvvv5();
 }else {
 //BA.debugLineNum = 288;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 290;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._v7 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim index As Object";
mostCurrent._v0 = new Object();
 //BA.debugLineNum = 26;BA.debugLine="Dim sv As SearchView";
mostCurrent._vv1 = new com.com7dolphin.heritage.searchview();
 //BA.debugLineNum = 28;BA.debugLine="Dim anOpenRightMenu, anCloseRightMenu As Animatio";
mostCurrent._vv2 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vv3 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim anOpenBlurPanel, anCloseBlurPanel As Animatio";
mostCurrent._vv4 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vv5 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim anOpenTopPanel, anCloseTopPanel As Animation";
mostCurrent._vv6 = new anywheresoftware.b4a.objects.AnimationWrapper();
mostCurrent._vv7 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim rightMenu As Panel";
mostCurrent._rightmenu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim mainPanel As Panel";
mostCurrent._mainpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim blurPanel As Panel";
mostCurrent._blurpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim rightMenuIsOpen, topPanelIsOpen As Boolean";
_vv0 = false;
_vvv1 = false;
 //BA.debugLineNum = 35;BA.debugLine="Dim toolsList As ListView";
mostCurrent._toolslist = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim MyWebViewExtras As WebViewExtras";
mostCurrent._vvv2 = new uk.co.martinpearman.b4a.webviewextras.WebViewExtras();
 //BA.debugLineNum = 38;BA.debugLine="Dim Javascript As String";
mostCurrent._vvv3 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim bitmap1 As Bitmap";
mostCurrent._vvv4 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim titleLabel As Label";
mostCurrent._titlelabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim topPanel As Panel";
mostCurrent._toppanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim favList As ListView";
mostCurrent._vvv5 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim favTitle As Label";
mostCurrent._vvv6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim cd As CustomDialog2";
mostCurrent._vvv7 = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2();
 //BA.debugLineNum = 46;BA.debugLine="Dim pnl As Panel";
mostCurrent._vvv0 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim fontSize As String = \"1em\"";
mostCurrent._vvvv1 = "1em";
 //BA.debugLineNum = 48;BA.debugLine="Dim s1,s2,s3,s4 As RadioButton";
mostCurrent._vvvv2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._vvvv3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._vvvv4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._vvvv5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _homeimg_click() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub homeImg_Click";
 //BA.debugLineNum = 201;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 378;BA.debugLine="Sub ImageView1_Click";
 //BA.debugLineNum = 379;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 380;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.7dolph";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://www.7dolphin.com")));
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static String  _imageview2_click() throws Exception{
 //BA.debugLineNum = 373;BA.debugLine="Sub ImageView2_Click";
 //BA.debugLineNum = 374;BA.debugLine="Main.pageTitle = \"ارث من\"";
mostCurrent._vvvvvvvvvvvvvvvvvv2._vvvvvvvvvvvvvvv6 = "ارث من";
 //BA.debugLineNum = 375;BA.debugLine="StartActivity(act3)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvvvvvvvvvvvvv5.getObject()));
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _menuimg_click() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub menuImg_Click";
 //BA.debugLineNum = 197;BA.debugLine="openRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv6();
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _openblurpanelanim_animationend() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Sub OpenBlurPanelAnim_AnimationEnd";
 //BA.debugLineNum = 173;BA.debugLine="blurPanel.Visible = True";
mostCurrent._blurpanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv6() throws Exception{
 //BA.debugLineNum = 221;BA.debugLine="Sub openRightMenu";
 //BA.debugLineNum = 222;BA.debugLine="rightMenuIsOpen = True";
_vv0 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 223;BA.debugLine="EnableAll(mainPanel, False)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._mainpanel,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="blurPanel.Visible = True";
mostCurrent._blurpanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 225;BA.debugLine="anOpenBlurPanel.Start(blurPanel)";
mostCurrent._vv4.Start((android.view.View)(mostCurrent._blurpanel.getObject()));
 //BA.debugLineNum = 226;BA.debugLine="anOpenRightMenu.Start(rightMenu)";
mostCurrent._vv2.Start((android.view.View)(mostCurrent._rightmenu.getObject()));
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv7() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub openTopPanel";
 //BA.debugLineNum = 237;BA.debugLine="topPanelIsOpen = True";
_vvv1 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 238;BA.debugLine="EnableAll(mainPanel, False)";
_vvvvvvvvvvvvvvvvvvvvv6(mostCurrent._mainpanel,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="blurPanel.Visible = True";
mostCurrent._blurpanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 240;BA.debugLine="anOpenBlurPanel.Start(blurPanel)";
mostCurrent._vv4.Start((android.view.View)(mostCurrent._blurpanel.getObject()));
 //BA.debugLineNum = 241;BA.debugLine="anOpenTopPanel.Start(topPanel)";
mostCurrent._vv6.Start((android.view.View)(mostCurrent._toppanel.getObject()));
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim SQL1 As SQL";
_v5 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 20;BA.debugLine="Dim dir As String";
_v6 = "";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _rightmenuanim_animationend() throws Exception{
 //BA.debugLineNum = 180;BA.debugLine="Sub rightMenuAnim_AnimationEnd";
 //BA.debugLineNum = 181;BA.debugLine="If Sender = anOpenRightMenu Then";
if ((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vv2.getObject()))) { 
 //BA.debugLineNum = 182;BA.debugLine="rightMenu.left = 50%x";
mostCurrent._rightmenu.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vv3.getObject()))) { 
 //BA.debugLineNum = 184;BA.debugLine="rightMenu.left = 100%x";
mostCurrent._rightmenu.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv4(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _t) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 59;BA.debugLine="Sub SetTypeface(parent As Panel, t As Typeface)";
 //BA.debugLineNum = 60;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group34 = _parent;
final int groupLen34 = group34.getSize();
for (int index34 = 0;index34 < groupLen34 ;index34++){
_v.setObject((android.view.View)(group34.Get(index34)));
 //BA.debugLineNum = 61;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 62;BA.debugLine="SetTypeface(v, t)";
_vvvvvvvvvvvvvvvvvvvvv4((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_t);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 64;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 65;BA.debugLine="lbl.Typeface = t";
_lbl.setTypeface((android.graphics.Typeface)(_t.getObject()));
 };
 }
;
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(String _value) throws Exception{
String _str = "";
String _pageurl = "";
 //BA.debugLineNum = 360;BA.debugLine="Sub sv_ItemClick(Value As String)";
 //BA.debugLineNum = 361;BA.debugLine="Dim str, pageURL As String";
_str = "";
_pageurl = "";
 //BA.debugLineNum = 362;BA.debugLine="Cursor1 = SQL1.ExecQuery(\"SELECT page FROM search";
mostCurrent._v7.setObject((android.database.Cursor)(_v5.ExecQuery("SELECT page FROM search where ind = '"+_value+"'")));
 //BA.debugLineNum = 363;BA.debugLine="Cursor1.Position = 0";
mostCurrent._v7.setPosition((int) (0));
 //BA.debugLineNum = 364;BA.debugLine="str = Cursor1.GetString(\"page\")";
_str = mostCurrent._v7.GetString("page");
 //BA.debugLineNum = 365;BA.debugLine="Cursor1.Close";
mostCurrent._v7.Close();
 //BA.debugLineNum = 367;BA.debugLine="titleLabel.Text = str.SubString2(0,str.IndexOf(\"-";
mostCurrent._titlelabel.setText((Object)(_str.substring((int) (0),_str.indexOf("-"))));
 //BA.debugLineNum = 368;BA.debugLine="pageURL = str.SubString(str.IndexOf(\"-\")+1)";
_pageurl = _str.substring((int) (_str.indexOf("-")+1));
 //BA.debugLineNum = 369;BA.debugLine="WebView1.LoadUrl(pageURL)";
mostCurrent._webview1.LoadUrl(_pageurl);
 //BA.debugLineNum = 370;BA.debugLine="closeTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv4();
 //BA.debugLineNum = 371;BA.debugLine="End Sub";
return "";
}
public static String  _toolslist_itemclick(int _position,Object _value) throws Exception{
String _myurl = "";
anywheresoftware.b4a.objects.IntentWrapper _share1 = null;
int _ret = 0;
 //BA.debugLineNum = 305;BA.debugLine="Sub toolsList_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 306;BA.debugLine="If Value = \"fav\" Then";
if ((_value).equals((Object)("fav"))) { 
 //BA.debugLineNum = 308;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 309;BA.debugLine="topPanel.RemoveAllViews";
mostCurrent._toppanel.RemoveAllViews();
 //BA.debugLineNum = 310;BA.debugLine="topPanel.AddView(favTitle,2%x,1%x,96%x,7%y)";
mostCurrent._toppanel.AddView((android.view.View)(mostCurrent._vvv6.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (96),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA));
 //BA.debugLineNum = 311;BA.debugLine="topPanel.AddView(favList,0,7%y,100%x,43%y)";
mostCurrent._toppanel.AddView((android.view.View)(mostCurrent._vvv5.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (43),mostCurrent.activityBA));
 //BA.debugLineNum = 312;BA.debugLine="buildFav";
_vvvvvvvvvvvvvvvvvvvvvv5();
 //BA.debugLineNum = 313;BA.debugLine="openTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv7();
 }else if((_value).equals((Object)("addfav"))) { 
 //BA.debugLineNum = 316;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 317;BA.debugLine="Dim myUrl As String = WebView1.Url";
_myurl = mostCurrent._webview1.getUrl();
 //BA.debugLineNum = 318;BA.debugLine="SQL1.ExecNonQuery(\"insert into favs values('\" &";
_v5.ExecNonQuery("insert into favs values('"+mostCurrent._titlelabel.getText()+"-"+_myurl+"')");
 //BA.debugLineNum = 319;BA.debugLine="ToastMessageShow(\"به لیست علاقه مندی اضافه شد\",F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("به لیست علاقه مندی اضافه شد",anywheresoftware.b4a.keywords.Common.False);
 }else if((_value).equals((Object)("about"))) { 
 //BA.debugLineNum = 322;BA.debugLine="titleLabel.Text = \"درباره ما\"";
mostCurrent._titlelabel.setText((Object)("درباره ما"));
 //BA.debugLineNum = 323;BA.debugLine="WebView1.LoadUrl(\"file:///android_asset/data/abo";
mostCurrent._webview1.LoadUrl("file:///android_asset/data/about.html#menu");
 //BA.debugLineNum = 324;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 }else if((_value).equals((Object)("search"))) { 
 //BA.debugLineNum = 327;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 328;BA.debugLine="topPanel.RemoveAllViews";
mostCurrent._toppanel.RemoveAllViews();
 //BA.debugLineNum = 329;BA.debugLine="sv.AddToParent(topPanel, 1%x, 1%y, 98%x, 49%y)";
mostCurrent._vv1._vvvvvvvvvvvvvvvvv1(mostCurrent._toppanel,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (98),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (49),mostCurrent.activityBA));
 //BA.debugLineNum = 330;BA.debugLine="openTopPanel";
_vvvvvvvvvvvvvvvvvvvvvv7();
 }else if((_value).equals((Object)("share"))) { 
 //BA.debugLineNum = 333;BA.debugLine="Dim share1 As Intent";
_share1 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 334;BA.debugLine="share1.Initialize(share1.ACTION_SEND,\"\")";
_share1.Initialize(_share1.ACTION_SEND,"");
 //BA.debugLineNum = 335;BA.debugLine="share1.SetType(\"text/plain\")";
_share1.SetType("text/plain");
 //BA.debugLineNum = 336;BA.debugLine="share1.PutExtra(\"android.intent.extra.TEXT\", \"ht";
_share1.PutExtra("android.intent.extra.TEXT",(Object)("http://cafebazaar.ir/app/com.com7dolphin.heritage/"));
 //BA.debugLineNum = 337;BA.debugLine="share1.WrapAsIntentChooser(\"Share via\")";
_share1.WrapAsIntentChooser("Share via");
 //BA.debugLineNum = 338;BA.debugLine="StartActivity(share1)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_share1.getObject()));
 }else if((_value).equals((Object)("font"))) { 
 //BA.debugLineNum = 341;BA.debugLine="closeRightMenu";
_vvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 342;BA.debugLine="Dim ret As Int";
_ret = 0;
 //BA.debugLineNum = 343;BA.debugLine="ret = cd.Show(\"تغییر اندازه قلم\", \"بلی\", \"خیر\",";
_ret = mostCurrent._vvv7.Show("تغییر اندازه قلم","بلی","خیر","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 344;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 345;BA.debugLine="If s1.Checked Then";
if (mostCurrent._vvvv2.getChecked()) { 
 //BA.debugLineNum = 346;BA.debugLine="fontSize = \"0.7em\"";
mostCurrent._vvvv1 = "0.7em";
 }else if(mostCurrent._vvvv3.getChecked()) { 
 //BA.debugLineNum = 348;BA.debugLine="fontSize = \"1em\"";
mostCurrent._vvvv1 = "1em";
 }else if(mostCurrent._vvvv4.getChecked()) { 
 //BA.debugLineNum = 350;BA.debugLine="fontSize = \"1.6em\"";
mostCurrent._vvvv1 = "1.6em";
 }else if(mostCurrent._vvvv5.getChecked()) { 
 //BA.debugLineNum = 352;BA.debugLine="fontSize = \"2.2em\"";
mostCurrent._vvvv1 = "2.2em";
 };
 //BA.debugLineNum = 354;BA.debugLine="Javascript=\"document.getElementById(\"\"myP\"\").st";
mostCurrent._vvv3 = "document.getElementById(\"myP\").style.fontSize=\""+mostCurrent._vvvv1+"\"";
 //BA.debugLineNum = 355;BA.debugLine="MyWebViewExtras.executeJavascript(WebView1, Jav";
mostCurrent._vvv2.executeJavascript((android.webkit.WebView)(mostCurrent._webview1.getObject()),mostCurrent._vvv3);
 };
 };
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _toppanelanim_animationend() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub TopPanelAnim_AnimationEnd";
 //BA.debugLineNum = 189;BA.debugLine="If Sender = anOpenTopPanel Then";
if ((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vv6.getObject()))) { 
 //BA.debugLineNum = 190;BA.debugLine="topPanel.top = 0";
mostCurrent._toppanel.setTop((int) (0));
 }else if((anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)).equals((Object)(mostCurrent._vv7.getObject()))) { 
 //BA.debugLineNum = 192;BA.debugLine="topPanel.top = -50%y";
mostCurrent._toppanel.setTop((int) (-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
}
