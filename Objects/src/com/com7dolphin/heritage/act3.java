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

public class act3 extends Activity implements B4AActivity{
	public static act3 mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.com7dolphin.heritage", "com.com7dolphin.heritage.act3");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (act3).");
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
		activityBA = new BA(this, layout, processBA, "com.com7dolphin.heritage", "com.com7dolphin.heritage.act3");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.com7dolphin.heritage.act3", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (act3) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (act3) Resume **");
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
		return act3.class;
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
        BA.LogInfo("** Activity (act3) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (act3) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _titlelabel = null;
public com.com7dolphin.heritage.customlistview _vvvvvv6 = null;
public static int[] _vvvvvv7 = null;
public static int _vvvvvv0 = 0;
public static int _vvvvvvv1 = 0;
public static int _vvvvvvv2 = 0;
public static int _vvvvvvv3 = 0;
public static int _vvvvvvv4 = 0;
public static int _vvvvvvv5 = 0;
public static int _vvvvvvv6 = 0;
public static int _vvvvvvv7 = 0;
public static int _vvvvvvv0 = 0;
public static int _vvvvvvvv1 = 0;
public static int _vvvvvvvv2 = 0;
public static int _vvvvvvvv3 = 0;
public static int _vvvvvvvv4 = 0;
public static int _vvvvvvvv5 = 0;
public static int _vvvvvvvv6 = 0;
public static int _vvvvvvvv7 = 0;
public static int _vvvvvvvv0 = 0;
public static int _vvvvvvvvv1 = 0;
public static float _vvvvvvvvv2 = 0f;
public static float _vvvvvvvvv3 = 0f;
public static float _vvvvvvvvv4 = 0f;
public static float _vvvvvvvvv5 = 0f;
public static float _vvvvvvvvv6 = 0f;
public static float _vvvvvvvvv7 = 0f;
public anywheresoftware.b4a.objects.collections.List _vvvvvvvvv0 = null;
public static int _vvvvvvvvvv1 = 0;
public static int _vvvvvvvvvv2 = 0;
public static int _vvvvvvvvvv3 = 0;
public static int _vvvvvvvvvv4 = 0;
public static int _vvvvvvvvvv5 = 0;
public static int _vvvvvvvvvv6 = 0;
public static int _vvvvvvvvvv7 = 0;
public static int _vvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvv4 = 0;
public static int _vvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvv7 = 0;
public static int _vvvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvv4 = 0;
public static int _vvvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvvv7 = 0;
public static int _vvvvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvv4 = 0;
public static int _vvvvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvvvv7 = 0;
public static int _vvvvvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvvv4 = 0;
public static int _vvvvvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvvvvv6 = 0;
public static int _vvvvvvvvvvvvvv7 = 0;
public static int _vvvvvvvvvvvvvv0 = 0;
public static int _vvvvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvvvv4 = 0;
public anywheresoftware.b4a.objects.PanelWrapper _mainpanel = null;
public com.com7dolphin.heritage.main _vvvvvvvvvvvvvvvvvv2 = null;
public com.com7dolphin.heritage.act2 _vvvvvvvvvvvvvvvvvv3 = null;
public com.com7dolphin.heritage.dbutils _vvvvvvvvvvvvvvvvvv4 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"LayErth\")";
mostCurrent._activity.LoadLayout("LayErth",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="titleLabel.Text = Main.pageTitle";
mostCurrent._titlelabel.setText((Object)(mostCurrent._vvvvvvvvvvvvvvvvvv2._vvvvvvvvvvvvvvv6));
 //BA.debugLineNum = 32;BA.debugLine="sbList.Initialize";
mostCurrent._vvvvvvvvv0.Initialize();
 //BA.debugLineNum = 34;BA.debugLine="Dim myFont As Typeface";
_myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 35;BA.debugLine="myFont= Typeface.LoadFromAssets(\"BYekan.ttf\")";
_myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("BYekan.ttf")));
 //BA.debugLineNum = 36;BA.debugLine="SetTypeface(mainPanel,myFont)";
_vvvvvvvvvvvvvvvvvvvvv4(mostCurrent._mainpanel,_myfont);
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
int _i = 0;
 //BA.debugLineNum = 66;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 67;BA.debugLine="Label2.Text = \"\"";
mostCurrent._label2.setText((Object)(""));
 //BA.debugLineNum = 68;BA.debugLine="For i=0 To 51";
{
final int step48 = 1;
final int limit48 = (int) (51);
for (_i = (int) (0); (step48 > 0 && _i <= limit48) || (step48 < 0 && _i >= limit48); _i = ((int)(0 + _i + step48))) {
 //BA.debugLineNum = 69;BA.debugLine="T(i) = 0";
_vvvvvv7[_i] = (int) (0);
 }
};
 //BA.debugLineNum = 71;BA.debugLine="W=-1";
_vvvvvvvvv1 = (int) (-1);
 //BA.debugLineNum = 72;BA.debugLine="Q=100";
_vvvvvvvvv2 = (float) (100);
 //BA.debugLineNum = 73;BA.debugLine="Q1=0";
_vvvvvvvvv3 = (float) (0);
 //BA.debugLineNum = 74;BA.debugLine="Q2=0";
_vvvvvvvvv4 = (float) (0);
 //BA.debugLineNum = 75;BA.debugLine="temp=0";
_vvvvvvvvv5 = (float) (0);
 //BA.debugLineNum = 76;BA.debugLine="sum=0";
_vvvvvvvvv6 = (float) (0);
 //BA.debugLineNum = 77;BA.debugLine="xsum=0";
_vvvvvvvvv7 = (float) (0);
 //BA.debugLineNum = 78;BA.debugLine="POW=0";
_vvvvvvv7 = (int) (0);
 //BA.debugLineNum = 79;BA.debugLine="WHP=0";
_vvvvvv0 = (int) (0);
 //BA.debugLineNum = 80;BA.debugLine="QMN=0";
_vvvvvvv1 = (int) (0);
 //BA.debugLineNum = 81;BA.debugLine="CMO=0";
_vvvvvvv2 = (int) (0);
 //BA.debugLineNum = 82;BA.debugLine="PQN=0";
_vvvvvvv3 = (int) (0);
 //BA.debugLineNum = 83;BA.debugLine="WNP=0";
_vvvvvvv4 = (int) (0);
 //BA.debugLineNum = 84;BA.debugLine="CLQ=0";
_vvvvvvv5 = (int) (0);
 //BA.debugLineNum = 85;BA.debugLine="DON=0";
_vvvvvvv6 = (int) (0);
 //BA.debugLineNum = 86;BA.debugLine="CK=0";
_vvvvvvv0 = (int) (0);
 //BA.debugLineNum = 87;BA.debugLine="MSUM=0";
_vvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 88;BA.debugLine="WPSUM=0";
_vvvvvvvv4 = (int) (0);
 //BA.debugLineNum = 89;BA.debugLine="WSUM=0";
_vvvvvvvv3 = (int) (0);
 //BA.debugLineNum = 90;BA.debugLine="PP=0";
_vvvvvvvv5 = (int) (0);
 //BA.debugLineNum = 91;BA.debugLine="M1=0";
_vvvvvvvv1 = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="M2=0";
_vvvvvvvv2 = (int) (0);
 //BA.debugLineNum = 93;BA.debugLine="KSUM=0";
_vvvvvvvv7 = (int) (0);
 //BA.debugLineNum = 94;BA.debugLine="KPSUM=0";
_vvvvvvvv0 = (int) (0);
 //BA.debugLineNum = 95;BA.debugLine="startCalc";
_vvvvvvvvvvvvvvvvvvvvvv0();
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvvv1() throws Exception{
int _l1 = 0;
 //BA.debugLineNum = 665;BA.debugLine="Sub calc1";
 //BA.debugLineNum = 667;BA.debugLine="If (W=4 OR W=5) AND T(3)=1 Then";
if ((_vvvvvvvvv1==4 || _vvvvvvvvv1==5) && _vvvvvv7[(int) (3)]==1) { 
 //BA.debugLineNum = 668;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 669;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0) { 
 //BA.debugLineNum = 671;BA.debugLine="Label2.Text = NumberFormat(Q1,0,4) & \" درصد ارث";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv3,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 672;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0) { 
 //BA.debugLineNum = 674;BA.debugLine="Label2.Text = NumberFormat(Q1,0,4) & \" درصد ارث";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv3,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 675;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==2 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvvvvvv1==1 && _vvvvvvvvvv2==0) { 
 //BA.debugLineNum = 677;BA.debugLine="Label2.Text = NumberFormat(Q1,0,4) & \" درصد ارث";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv3,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 678;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1) { 
 //BA.debugLineNum = 680;BA.debugLine="Label2.Text = NumberFormat(Q1,0,4) & \" درصد ارث";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv3,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 681;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==2 || _vvvvvvvvv1==3) && (_vvvvvv7[(int) (3)]==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==0)) { 
 //BA.debugLineNum = 683;BA.debugLine="Dim l1 As Int = 2*Np + Nd";
_l1 = (int) (2*_vvvvvvvvvv1+_vvvvvvvvvv2);
 //BA.debugLineNum = 684;BA.debugLine="If W=2 Then";
if (_vvvvvvvvv1==2) { 
 //BA.debugLineNum = 685;BA.debugLine="temp = (2/l1)*Q1";
_vvvvvvvvv5 = (float) ((2/(double)_l1)*_vvvvvvvvv3);
 }else if(_vvvvvvvvv1==3) { 
 //BA.debugLineNum = 687;BA.debugLine="temp = Q1/l1";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_l1);
 };
 //BA.debugLineNum = 689;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 690;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && (_vvvvvvvvvv1>0 || _vvvvvvvvvv2>1)) { 
 //BA.debugLineNum = 692;BA.debugLine="temp = (1/6)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 693;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 694;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==1 && (_vvvvvvvvvv1>0 || _vvvvvvvvvv2>1)) { 
 //BA.debugLineNum = 696;BA.debugLine="temp = (1/6)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 697;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 698;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==2 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1) { 
 //BA.debugLineNum = 700;BA.debugLine="temp = (4/6)*Q1*(2/(2*Np+Nd))";
_vvvvvvvvv5 = (float) ((4/(double)6)*_vvvvvvvvv3*(2/(double)(2*_vvvvvvvvvv1+_vvvvvvvvvv2)));
 //BA.debugLineNum = 701;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 702;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && (_vvvvvvvvvv1>0 || _vvvvvvvvvv2>1)) { 
 //BA.debugLineNum = 704;BA.debugLine="temp = (4/6)*Q1*(1/(2*Np+Nd))";
_vvvvvvvvv5 = (float) ((4/(double)6)*_vvvvvvvvv3*(1/(double)(2*_vvvvvvvvvv1+_vvvvvvvvvv2)));
 //BA.debugLineNum = 705;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 706;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1>0) { 
 //BA.debugLineNum = 708;BA.debugLine="temp = (1/6)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 709;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 710;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1>0) { 
 //BA.debugLineNum = 712;BA.debugLine="temp = (1/6)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 713;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 714;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==2 && ((_vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==0 && _vvvvvvvvvv1>0) || (_vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvvvvvv1>0))) { 
 //BA.debugLineNum = 716;BA.debugLine="temp = (10*Q1)/((12*Np)+(6*Nd))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv1)+(6*_vvvvvvvvvv2)));
 //BA.debugLineNum = 717;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 718;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && ((_vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==0 && _vvvvvvvvvv1>0) || (_vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvvvvvv1>0))) { 
 //BA.debugLineNum = 720;BA.debugLine="temp = (5*Q1)/((12*Np)+(6*Nd))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv1)+(6*_vvvvvvvvvv2)));
 //BA.debugLineNum = 721;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 722;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1) { 
 //BA.debugLineNum = 724;BA.debugLine="temp = (1/4)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 725;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 726;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1) { 
 //BA.debugLineNum = 728;BA.debugLine="temp = (1/4)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 729;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 730;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && ((_vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (1)]==1) || (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (1)]==0))) { 
 //BA.debugLineNum = 732;BA.debugLine="temp = (3/4)*Q1";
_vvvvvvvvv5 = (float) ((3/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 733;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 734;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2>1) { 
 //BA.debugLineNum = 736;BA.debugLine="temp = (1/5)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 737;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 738;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2>1) { 
 //BA.debugLineNum = 740;BA.debugLine="temp = (1/5)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 741;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 742;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2>1 && ((_vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (1)]==1) || (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (1)]==0))) { 
 //BA.debugLineNum = 744;BA.debugLine="temp = (4/(5*Nd))*Q1";
_vvvvvvvvv5 = (float) ((4/(double)(5*_vvvvvvvvvv2))*_vvvvvvvvv3);
 //BA.debugLineNum = 745;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 746;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==4 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1) { 
 //BA.debugLineNum = 748;BA.debugLine="temp = (2*Q1)/(2*Mp+Md)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvv3+_vvvvvvvvvv4));
 //BA.debugLineNum = 749;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 750;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1) { 
 //BA.debugLineNum = 752;BA.debugLine="temp = Q1/(2*Mp+Md)";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)(2*_vvvvvvvvvv3+_vvvvvvvvvv4));
 //BA.debugLineNum = 753;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 754;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && (_vvvvvvvvvv3>0 || _vvvvvvvvvv4>1)) { 
 //BA.debugLineNum = 756;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 757;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 758;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==0 && (_vvvvvvvvvv3>0 || _vvvvvvvvvv4>1)) { 
 //BA.debugLineNum = 760;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 761;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 762;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==4 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1) { 
 //BA.debugLineNum = 764;BA.debugLine="temp = (8*Q1)/((12*Mp)+(6*Md))";
_vvvvvvvvv5 = (float) ((8*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv3)+(6*_vvvvvvvvvv4)));
 //BA.debugLineNum = 765;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 766;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && (_vvvvvvvvvv3>0 || _vvvvvvvvvv4>1)) { 
 //BA.debugLineNum = 768;BA.debugLine="temp = (4*Q1)/((12*Mp)+(6*Md))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv3)+(6*_vvvvvvvvvv4)));
 //BA.debugLineNum = 769;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 770;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3>0) { 
 //BA.debugLineNum = 772;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 773;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 774;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3>0) { 
 //BA.debugLineNum = 776;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 777;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 778;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==4 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3>0) { 
 //BA.debugLineNum = 780;BA.debugLine="temp = (10*Q1)/((12*Mp)+(6*Md))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv3)+(6*_vvvvvvvvvv4)));
 //BA.debugLineNum = 781;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 782;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==4 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3>0) { 
 //BA.debugLineNum = 784;BA.debugLine="temp = (10*Q1)/((12*Mp)+(6*Md))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv3)+(6*_vvvvvvvvvv4)));
 //BA.debugLineNum = 785;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 786;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && ((_vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==0 && _vvvvvvvvvv3>0) || (_vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvvvvvv3>0))) { 
 //BA.debugLineNum = 788;BA.debugLine="temp = (5*Q1)/((12*Mp)+(6*Md))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)((12*_vvvvvvvvvv3)+(6*_vvvvvvvvvv4)));
 //BA.debugLineNum = 789;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 790;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1) { 
 //BA.debugLineNum = 792;BA.debugLine="temp = (1/4)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 793;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 794;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1) { 
 //BA.debugLineNum = 796;BA.debugLine="temp = (1/4)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 797;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 798;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && ((_vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (1)]==1) || (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (1)]==0))) { 
 //BA.debugLineNum = 800;BA.debugLine="temp = (3/4)*Q1";
_vvvvvvvvv5 = (float) ((3/(double)4)*_vvvvvvvvv3);
 //BA.debugLineNum = 801;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 802;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4>1) { 
 //BA.debugLineNum = 804;BA.debugLine="temp = (1/5)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 805;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 806;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==0 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4>1) { 
 //BA.debugLineNum = 808;BA.debugLine="temp = (1/5)*Q1";
_vvvvvvvvv5 = (float) ((1/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 809;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 810;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4>1 && ((_vvvvvv7[(int) (2)]==0 && _vvvvvv7[(int) (1)]==1) || (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (1)]==0))) { 
 //BA.debugLineNum = 812;BA.debugLine="temp = (4*Q1)/(5*Md)";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(5*_vvvvvvvvvv4));
 //BA.debugLineNum = 813;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 814;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 818;BA.debugLine="T(9)=ynBox(\"آیا فوت شده (دو برادر) یا (چهار خواهر";
_vvvvvv7[(int) (9)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده (دو برادر) یا (چهار خواهر) یا (یک برادر و دو خواهر) دارد؟");
 //BA.debugLineNum = 820;BA.debugLine="If W=0 AND (T(2)=1 AND T(3)=0 AND T(4)=0 AND T(9)";
if (_vvvvvvvvv1==0 && (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0 && _vvvvvv7[(int) (9)]==0)) { 
 //BA.debugLineNum = 821;BA.debugLine="temp = (2/3) *Q1";
_vvvvvvvvv5 = (float) ((2/(double)3)*_vvvvvvvvv3);
 //BA.debugLineNum = 822;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 823;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && (_vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0 && _vvvvvv7[(int) (9)]==0)) { 
 //BA.debugLineNum = 825;BA.debugLine="temp = (1/3) *Q1";
_vvvvvvvvv5 = (float) ((1/(double)3)*_vvvvvvvvv3);
 //BA.debugLineNum = 826;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 827;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && (_vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0 && _vvvvvv7[(int) (9)]==1)) { 
 //BA.debugLineNum = 829;BA.debugLine="temp = (5/6) *Q1";
_vvvvvvvvv5 = (float) ((5/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 830;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 831;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && (_vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0 && _vvvvvv7[(int) (9)]==1)) { 
 //BA.debugLineNum = 833;BA.debugLine="temp = (1/6) *Q1";
_vvvvvvvvv5 = (float) ((1/(double)6)*_vvvvvvvvv3);
 //BA.debugLineNum = 834;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 835;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 837;BA.debugLine="temp = Q1/5";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)5);
 //BA.debugLineNum = 838;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 839;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 841;BA.debugLine="temp = Q1/5";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)5);
 //BA.debugLineNum = 842;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 843;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 845;BA.debugLine="temp = (3/5)*Q1";
_vvvvvvvvv5 = (float) ((3/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 846;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 847;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 849;BA.debugLine="temp = (5/24)*Q1";
_vvvvvvvvv5 = (float) ((5/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 850;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 851;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 853;BA.debugLine="temp = (4/24)*Q1";
_vvvvvvvvv5 = (float) ((4/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 854;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 855;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==3 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==1 && _vvvvvvvvvv1==0 && _vvvvvvvvvv2==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 857;BA.debugLine="temp = (15/24)*Q1";
_vvvvvvvvv5 = (float) ((15/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 858;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 859;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 861;BA.debugLine="temp = Q1/5";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)5);
 //BA.debugLineNum = 862;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 863;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 865;BA.debugLine="temp = Q1/5";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)5);
 //BA.debugLineNum = 866;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 867;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==0) { 
 //BA.debugLineNum = 869;BA.debugLine="temp = (3/5)*Q1";
_vvvvvvvvv5 = (float) ((3/(double)5)*_vvvvvvvvv3);
 //BA.debugLineNum = 870;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 871;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==0 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 873;BA.debugLine="temp = (5/24)*Q1";
_vvvvvvvvv5 = (float) ((5/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 874;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 875;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==1 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 877;BA.debugLine="temp = (4/24)*Q1";
_vvvvvvvvv5 = (float) ((4/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 878;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 879;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==5 && _vvvvvv7[(int) (1)]==1 && _vvvvvv7[(int) (2)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1 && _vvvvvvvvvv3==0 && _vvvvvvvvvv4==1 && _vvvvvv7[(int) (9)]==1) { 
 //BA.debugLineNum = 881;BA.debugLine="temp = (15/24)*Q1";
_vvvvvvvvv5 = (float) ((15/(double)24)*_vvvvvvvvv3);
 //BA.debugLineNum = 882;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 883;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 885;BA.debugLine="Label2.Text = \"حالت استثنا! شما می توانید با توس";
mostCurrent._label2.setText((Object)("حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"));
 //BA.debugLineNum = 886;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 888;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvvv3() throws Exception{
 //BA.debugLineNum = 890;BA.debugLine="Sub calc2";
 //BA.debugLineNum = 893;BA.debugLine="WHP = 0";
_vvvvvv0 = (int) (0);
 //BA.debugLineNum = 894;BA.debugLine="If (T(17)=1 AND T(18)=1) _ 		OR (T(17)=1 AND T(18";
if ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==1) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0>1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1 && _vvvvvvvvvvv7>1) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (25)]==1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==1 && _vvvvvv7[(int) (26)]==1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==1 && _vvvvvvvvvvv1>1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (25)]==1 && _vvvvvvvvvvv2>1)) { 
 //BA.debugLineNum = 902;BA.debugLine="WHP=1";
_vvvvvv0 = (int) (1);
 };
 //BA.debugLineNum = 905;BA.debugLine="CMO = 0";
_vvvvvvv2 = (int) (0);
 //BA.debugLineNum = 906;BA.debugLine="If  isZero(17,20)  AND isZero(25,26) Then";
if (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (20)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26))) { 
 //BA.debugLineNum = 907;BA.debugLine="CMO=1";
_vvvvvvv2 = (int) (1);
 };
 //BA.debugLineNum = 910;BA.debugLine="QMN = 0";
_vvvvvvv1 = (int) (0);
 //BA.debugLineNum = 911;BA.debugLine="If  isZero(11,16)  AND isZero(21,24)  Then";
if (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (24))) { 
 //BA.debugLineNum = 912;BA.debugLine="QMN=1";
_vvvvvvv1 = (int) (1);
 };
 //BA.debugLineNum = 915;BA.debugLine="PQN = 0";
_vvvvvvv3 = (int) (0);
 //BA.debugLineNum = 916;BA.debugLine="If  T(21)=0 AND T(12)=0 AND  T(13)=0  AND T(14)=0";
if (_vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (12)]==0 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 917;BA.debugLine="PQN=1";
_vvvvvvv3 = (int) (1);
 };
 //BA.debugLineNum = 921;BA.debugLine="If W=9 AND ( T(11)=0 AND isZero(13,26) ) Then";
if (_vvvvvvvvv1==9 && (_vvvvvv7[(int) (11)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (26)))) { 
 //BA.debugLineNum = 922;BA.debugLine="temp = Q1";
_vvvvvvvvv5 = _vvvvvvvvv3;
 //BA.debugLineNum = 923;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 924;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvv7[(int) (12)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (26)))) { 
 //BA.debugLineNum = 926;BA.debugLine="temp = Q1";
_vvvvvvvvv5 = _vvvvvvvvv3;
 //BA.debugLineNum = 927;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 928;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (20),(int) (26)))) { 
 //BA.debugLineNum = 930;BA.debugLine="temp = Q1";
_vvvvvvvvv5 = _vvvvvvvvv3;
 //BA.debugLineNum = 931;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 932;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (19)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)))) { 
 //BA.debugLineNum = 934;BA.debugLine="temp = Q1";
_vvvvvvvvv5 = _vvvvvvvvv3;
 //BA.debugLineNum = 935;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 936;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvvvvvvvv4>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (20)) && _vvvvvv7[(int) (14)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvv7[(int) (22)]==0)) { 
 //BA.debugLineNum = 938;BA.debugLine="temp = Q1/a1b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvvv4);
 //BA.debugLineNum = 939;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 940;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==11 && _vvvvvvvvvvvv3>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (13)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (21)) && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0)) { 
 //BA.debugLineNum = 942;BA.debugLine="temp = Q1/a1k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvvv3);
 //BA.debugLineNum = 943;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 944;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvvvvvvvv2>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (16),(int) (22)) && _vvvvvv7[(int) (24)]==0 && _vvvvvv7[(int) (26)]==0)) { 
 //BA.debugLineNum = 946;BA.debugLine="temp = Q1/a2b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvvv2);
 //BA.debugLineNum = 947;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 948;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvvvvvvvv1>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (15)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (23)) && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0)) { 
 //BA.debugLineNum = 950;BA.debugLine="temp = Q1/a2k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvvv1);
 //BA.debugLineNum = 951;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 952;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==14 && _vvvvvvvvvvv0>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (18),(int) (24)) && _vvvvvv7[(int) (26)]==0)) { 
 //BA.debugLineNum = 954;BA.debugLine="temp = Q1/a3b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv0);
 //BA.debugLineNum = 955;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 956;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==15 && _vvvvvvvvvvv7>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (17)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (25)))) { 
 //BA.debugLineNum = 958;BA.debugLine="temp = Q1/a3k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv7);
 //BA.debugLineNum = 959;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 960;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvvvvvvv6>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvv7[(int) (22)]==0)) { 
 //BA.debugLineNum = 962;BA.debugLine="temp = Q1/b1b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv6);
 //BA.debugLineNum = 963;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 964;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvvvvvvv5>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (21)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)))) { 
 //BA.debugLineNum = 966;BA.debugLine="temp = Q1/b1k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv5);
 //BA.debugLineNum = 967;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 968;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvvvvvvv4>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (24),(int) (26)))) { 
 //BA.debugLineNum = 970;BA.debugLine="temp = Q1/b2b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv4);
 //BA.debugLineNum = 971;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 972;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvvvvvvv3>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (23)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)))) { 
 //BA.debugLineNum = 974;BA.debugLine="temp = Q1/b2k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv3);
 //BA.debugLineNum = 975;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 976;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==20 && _vvvvvvvvvvv2>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (24)) && _vvvvvv7[(int) (26)]==0)) { 
 //BA.debugLineNum = 978;BA.debugLine="temp = Q1/b3b";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv2);
 //BA.debugLineNum = 979;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 980;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==21 && _vvvvvvvvvvv1>0 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (25)))) { 
 //BA.debugLineNum = 982;BA.debugLine="temp = Q1/b3k";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvvv1);
 //BA.debugLineNum = 983;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 984;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 986;BA.debugLine="temp = (4*Q1)/9";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 987;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 988;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 990;BA.debugLine="temp = (2*Q1)/9";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 991;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 992;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 994;BA.debugLine="temp = Q1/3";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)3);
 //BA.debugLineNum = 995;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 996;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (19)]==1)) { 
 //BA.debugLineNum = 998;BA.debugLine="temp = Q1/3";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)3);
 //BA.debugLineNum = 999;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1000;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (26)) && _vvvvvv7[(int) (12)]==1)) { 
 //BA.debugLineNum = 1002;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1003;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1004;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (26)) && _vvvvvv7[(int) (11)]==1)) { 
 //BA.debugLineNum = 1006;BA.debugLine="temp = Q1/3";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)3);
 //BA.debugLineNum = 1007;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1008;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1010;BA.debugLine="temp = Q1/2";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)2);
 //BA.debugLineNum = 1011;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1012;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (19)]==1)) { 
 //BA.debugLineNum = 1014;BA.debugLine="temp = Q1/2";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)2);
 //BA.debugLineNum = 1015;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1016;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (12),(int) (19)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1018;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1019;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1020;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (12),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0)) { 
 //BA.debugLineNum = 1022;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1023;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1024;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (12),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (20)]==0)) { 
 //BA.debugLineNum = 1026;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1027;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1028;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (20)]==0)) { 
 //BA.debugLineNum = 1030;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1031;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1032;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (19)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1034;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1035;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1036;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (11)]==0)) { 
 //BA.debugLineNum = 1038;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1039;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1040;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (12),(int) (19)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1)) { 
 //BA.debugLineNum = 1042;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1043;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1044;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (19)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1)) { 
 //BA.debugLineNum = 1046;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1047;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1048;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (12)]==0 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1050;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1051;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1052;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1054;BA.debugLine="temp = (2*Q1)/3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1055;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1056;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (20)]==0)) { 
 //BA.debugLineNum = 1058;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1059;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1060;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1)) { 
 //BA.debugLineNum = 1062;BA.debugLine="temp = (1*Q1)/3";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)3);
 //BA.debugLineNum = 1063;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1064;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (12)]==0 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1066;BA.debugLine="temp = (1*Q1)/6";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 1067;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1068;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (12)]==0 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (19)]==1)) { 
 //BA.debugLineNum = 1070;BA.debugLine="temp = (1*Q1)/6";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 1071;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1072;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==81 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1074;BA.debugLine="temp = (1*Q1)/6";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 1075;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1076;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==91 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (12)]==1)) { 
 //BA.debugLineNum = 1078;BA.debugLine="temp = (1*Q1)/6";
_vvvvvvvvv5 = (float) ((1*_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 1079;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1080;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (20),(int) (26)) && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (12)]==1)) { 
 //BA.debugLineNum = 1082;BA.debugLine="temp = (4*Q1)/9";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 1083;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1084;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (20),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (19)]==1)) { 
 //BA.debugLineNum = 1086;BA.debugLine="temp = (2*Q1)/9";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 1087;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1088;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvv7[(int) (20)]==1)) { 
 //BA.debugLineNum = 1090;BA.debugLine="temp = (4*Q1)/9";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 1091;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1092;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (26)) && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvv7[(int) (19)]==0)) { 
 //BA.debugLineNum = 1094;BA.debugLine="temp = (2*Q1)/9";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)9);
 //BA.debugLineNum = 1095;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1096;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvv7[(int) (13)]==1 && _vvvvvvvvv1==16) || (_vvvvvv7[(int) (14)]==1 && _vvvvvvvvv1==17) || (_vvvvvv7[(int) (15)]==1 && _vvvvvvvvv1==18) || (_vvvvvv7[(int) (16)]==1 && _vvvvvvvvv1==19) || (_vvvvvv7[(int) (17)]==1 && _vvvvvvvvv1==20) || (_vvvvvv7[(int) (18)]==1 && _vvvvvvvvv1==21)) { 
 //BA.debugLineNum = 1098;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 1099;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1104;BA.debugLine="temp = (2*Q1)/L3";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)_vvvvvvvvvv6);
 //BA.debugLineNum = 1105;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1106;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1) { 
 //BA.debugLineNum = 1108;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5));
 //BA.debugLineNum = 1109;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1110;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1112;BA.debugLine="temp = Q1/L3";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)_vvvvvvvvvv6);
 //BA.debugLineNum = 1113;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1114;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) { 
 //BA.debugLineNum = 1116;BA.debugLine="temp = Q1/(2*b1b+a1k)";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3));
 //BA.debugLineNum = 1117;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1118;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1120;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3));
 //BA.debugLineNum = 1121;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1122;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (22)]==1) { 
 //BA.debugLineNum = 1124;BA.debugLine="temp = (2*Q1)/(2*b1b+b1k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5));
 //BA.debugLineNum = 1125;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1126;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1129;BA.debugLine="temp = (Q1)/(2*a1b+b1k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5));
 //BA.debugLineNum = 1130;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1131;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvv7[(int) (21)]==1) { 
 //BA.debugLineNum = 1133;BA.debugLine="temp = (Q1)/(2*b1b+b1k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5));
 //BA.debugLineNum = 1134;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1135;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==12 || _vvvvvvvvv1==13 || (_vvvvvv7[(int) (15)]==0 && _vvvvvvvvv1==18) || (_vvvvvv7[(int) (16)]==0 && _vvvvvvvvv1==19)) && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1))) { 
 //BA.debugLineNum = 1138;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 1139;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==12 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1143;BA.debugLine="temp = (2*Q1)/(L4)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv7));
 //BA.debugLineNum = 1144;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1145;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==12 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 1148;BA.debugLine="temp = (2*Q1)/(2*a2b+b2k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3));
 //BA.debugLineNum = 1149;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1150;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==13 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1155;BA.debugLine="temp = (Q1)/(L4)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv7));
 //BA.debugLineNum = 1156;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1157;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==13 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (23)]==1) { 
 //BA.debugLineNum = 1160;BA.debugLine="temp = (Q1)/(2*b1b+a2k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv1));
 //BA.debugLineNum = 1161;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1162;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==18 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1165;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1));
 //BA.debugLineNum = 1166;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1167;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==18 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 1170;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3));
 //BA.debugLineNum = 1171;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1172;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==19 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 1175;BA.debugLine="temp = (Q1)/(2*a2b+b2k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3));
 //BA.debugLineNum = 1176;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1177;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (17),(int) (18)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (25),(int) (26)) && _vvvvvvvvv1==19 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (23)]==1) { 
 //BA.debugLineNum = 1179;BA.debugLine="temp = (Q1)/(2*b2b+b2k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3));
 //BA.debugLineNum = 1180;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1181;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==14 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1185;BA.debugLine="temp = (Q1)/(L5)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv0));
 //BA.debugLineNum = 1186;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1187;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==14 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1) { 
 //BA.debugLineNum = 1190;BA.debugLine="temp = (Q1)/(a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1191;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1192;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==15 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1195;BA.debugLine="temp = (Q1)/(L5)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv0));
 //BA.debugLineNum = 1196;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1197;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==15 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==1) { 
 //BA.debugLineNum = 1201;BA.debugLine="temp = (Q1)/(b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1202;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1203;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1207;BA.debugLine="temp = (Q1)/(b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1208;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1209;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1) { 
 //BA.debugLineNum = 1212;BA.debugLine="temp = (Q1)/(b3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv2+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1213;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1214;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==21 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1217;BA.debugLine="temp = (Q1)/(a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1218;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1219;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (16)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==21 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==1) { 
 //BA.debugLineNum = 1224;BA.debugLine="temp = (Q1)/(b3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvvv2+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1225;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1226;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1)) && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1231;BA.debugLine="temp = (10*Q1)/(6*L3)";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)(6*_vvvvvvvvvv6));
 //BA.debugLineNum = 1232;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1233;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1)) && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1236;BA.debugLine="temp = (5*Q1)/(6*L3)";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*_vvvvvvvvvv6));
 //BA.debugLineNum = 1237;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1238;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv7[(int) (13)]==0 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1)) && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1241;BA.debugLine="temp = (10*Q1)/(6*(2*b3b+a1k))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvv2+_vvvvvvvvvvvv3)));
 //BA.debugLineNum = 1242;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1243;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1))) { 
 //BA.debugLineNum = 1246;BA.debugLine="temp = (10*Q1)/(6*(2*b1b+b1k))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1247;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1248;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1 && _vvvvvv7[(int) (22)]==1 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1))) { 
 //BA.debugLineNum = 1251;BA.debugLine="temp = (10*Q1)/(6*(2*a1b+b1k))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1252;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1253;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1))) { 
 //BA.debugLineNum = 1257;BA.debugLine="temp = (5*Q1)/(6*(2*b1b+a1k))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3)));
 //BA.debugLineNum = 1258;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1259;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (13)]==1 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1))) { 
 //BA.debugLineNum = 1262;BA.debugLine="temp = (5*Q1)/(6*(2*a1b+b1k))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1263;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1264;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1 && ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1))) { 
 //BA.debugLineNum = 1267;BA.debugLine="temp = (5*Q1)/(6*(2*b1b+b1k))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1268;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1269;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==14 && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1)) && _vvvvvv7[(int) (18)]==0 && _vvvvvvvvvvv0==1) { 
 //BA.debugLineNum = 1274;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 1275;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1276;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==15 && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1)) && _vvvvvv7[(int) (17)]==0 && _vvvvvvvvvvv7==1) { 
 //BA.debugLineNum = 1279;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 1280;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1281;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1)) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvvvvvvv2==1) { 
 //BA.debugLineNum = 1284;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 1285;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1286;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==21 && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1)) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvvvvvvv1==1) { 
 //BA.debugLineNum = 1289;BA.debugLine="temp = Q1/6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 1290;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1291;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && _vvvvvv0==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1296;BA.debugLine="temp = (4*Q1)/(3*L3)";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv6));
 //BA.debugLineNum = 1297;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1298;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==10 && _vvvvvv0==1 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1) { 
 //BA.debugLineNum = 1301;BA.debugLine="temp = (4*Q1)/(3*(2*a1b+b1k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1302;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1303;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1306;BA.debugLine="temp = (2*Q1)/(3*L3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv6));
 //BA.debugLineNum = 1307;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1308;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==11 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (21)]==1) { 
 //BA.debugLineNum = 1312;BA.debugLine="temp = (2*Q1)/(3*(2*b1b+a1k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3)));
 //BA.debugLineNum = 1313;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1314;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1317;BA.debugLine="temp = (4*Q1)/(3*(2*b1b+a1k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3)));
 //BA.debugLineNum = 1318;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1319;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==16 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (22)]==1) { 
 //BA.debugLineNum = 1322;BA.debugLine="temp = (4*Q1)/(3*(2*b1b+b1k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1323;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1324;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1327;BA.debugLine="temp = (2*Q1)/(3*(2*a1b+b1k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1328;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1329;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==17 && _vvvvvv0==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (21)]==1) { 
 //BA.debugLineNum = 1333;BA.debugLine="temp = (2*Q1)/(3*(2*b1b+b1k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5)));
 //BA.debugLineNum = 1334;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1335;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==14 && _vvvvvv0==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1340;BA.debugLine="temp = (Q1)/(3*L5)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv0));
 //BA.debugLineNum = 1341;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1342;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==14 && _vvvvvv0==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1) { 
 //BA.debugLineNum = 1345;BA.debugLine="temp = (Q1)/(3*(a3b+b3k))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv0+_vvvvvvvvvvv1)));
 //BA.debugLineNum = 1346;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1347;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==15 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1350;BA.debugLine="temp = (Q1)/(3*L5)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv0));
 //BA.debugLineNum = 1351;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1352;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==15 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==1) { 
 //BA.debugLineNum = 1355;BA.debugLine="temp = (Q1)/(3*(a3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv7+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1356;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1357;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1360;BA.debugLine="temp = (Q1)/(3*(a3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv7+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1361;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1362;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1365;BA.debugLine="temp = (Q1)/(3*(a3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv7+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1366;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1367;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1370;BA.debugLine="temp = (Q1)/(3*(a3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv7+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1371;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1372;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==20 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1) { 
 //BA.debugLineNum = 1375;BA.debugLine="temp = (Q1)/(3*(b3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv1+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1376;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1377;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==21 && _vvvvvv0==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1383;BA.debugLine="temp = (Q1)/(3*(a3b+b3k))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv0+_vvvvvvvvvvv1)));
 //BA.debugLineNum = 1384;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1385;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (12)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (20)) && _vvvvvvvvv1==21 && _vvvvvv0==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (25)]==1) { 
 //BA.debugLineNum = 1388;BA.debugLine="temp = (Q1)/(3*(b3k+b3b))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvvvvv1+_vvvvvvvvvvv2)));
 //BA.debugLineNum = 1389;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1390;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==12 && _vvvvvv0==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1394;BA.debugLine="temp = (4*Q1)/(3*L4)";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv7));
 //BA.debugLineNum = 1395;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1396;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==12 && _vvvvvv0==1 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 1399;BA.debugLine="temp = (4*Q1)/(3*(2*a2b+b2k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3)));
 //BA.debugLineNum = 1400;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1401;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==13 && _vvvvvv0==1 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1404;BA.debugLine="temp = (2*Q1)/(3*L4)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*_vvvvvvvvvv7));
 //BA.debugLineNum = 1405;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1406;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==13 && _vvvvvv0==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (23)]==1) { 
 //BA.debugLineNum = 1411;BA.debugLine="temp = (2*Q1)/(3*(2*b2b+a2k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1)));
 //BA.debugLineNum = 1412;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1413;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==18 && _vvvvvv0==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1416;BA.debugLine="temp = (4*Q1)/(3*(2*b2b+a2k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1)));
 //BA.debugLineNum = 1417;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1418;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==18 && _vvvvvv0==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 1421;BA.debugLine="temp = (4*Q1)/(3*(2*b2b+b2k))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3)));
 //BA.debugLineNum = 1422;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1423;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==19 && _vvvvvv0==1 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1426;BA.debugLine="temp = (2*Q1)/(3*(2*a2b+b3k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv1)));
 //BA.debugLineNum = 1427;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1428;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (19),(int) (22)) && _vvvvvvvvv1==19 && _vvvvvv0==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (23)]==1) { 
 //BA.debugLineNum = 1431;BA.debugLine="temp = (2*Q1)/(3*(2*b2b+b2k))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3)));
 //BA.debugLineNum = 1432;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1433;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==1 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1438;BA.debugLine="temp = (Q1)/(2+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1439;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1440;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==1 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1443;BA.debugLine="temp = (Q1)/(2+a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1444;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1445;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==1 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1447;BA.debugLine="temp = (Q1)/(2+b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1448;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1449;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==1 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1452;BA.debugLine="temp = (Q1)/(2+b3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv2+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1453;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1454;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==0 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1456;BA.debugLine="temp = (Q1)/(1+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1457;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1458;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==0 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1461;BA.debugLine="temp = (Q1)/(1+a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1462;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1463;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==0 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1467;BA.debugLine="temp = (Q1)/(1+b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1468;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1469;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==81 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1) || (_vvvvvvvvv1==91 && _vvvvvv7[(int) (19)]==0 && _vvvvvvv1==1) && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1472;BA.debugLine="temp = (Q1)/(1+b3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv2+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1473;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1474;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==14 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1479;BA.debugLine="temp = (Q1)/(2+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1480;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1481;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==14 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1485;BA.debugLine="temp = (Q1)/(2+a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1486;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1487;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==14 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1490;BA.debugLine="temp = (Q1)/(1+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1491;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1492;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==14 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1495;BA.debugLine="temp = (Q1)/(1+a3b+b3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv0+_vvvvvvvvvvv1));
 //BA.debugLineNum = 1496;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1497;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==15 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1500;BA.debugLine="temp = (Q1)/(2+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1501;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1502;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==15 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0) { 
 //BA.debugLineNum = 1505;BA.debugLine="temp = (Q1)/(2+b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1506;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1507;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==15 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1510;BA.debugLine="temp = (Q1)/(1+a3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv0+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1511;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1512;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==15 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0) { 
 //BA.debugLineNum = 1514;BA.debugLine="temp = (Q1)/(1+b3b+a3k)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv2+_vvvvvvvvvvv7));
 //BA.debugLineNum = 1515;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1516;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==20 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1522;BA.debugLine="temp = (Q1)/(2+a3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv7+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1523;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1524;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==20 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1527;BA.debugLine="temp = (Q1)/(1+a3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv7+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1528;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1529;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==20 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 1532;BA.debugLine="temp = (Q1)/(1+b3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv1+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1533;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1534;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==20 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (18)]==0) { 
 //BA.debugLineNum = 1537;BA.debugLine="temp = (Q1)/(2+b3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv1+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1538;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1539;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==21 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1542;BA.debugLine="temp = (Q1)/(2+b3k+a3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv1+_vvvvvvvvvvv0));
 //BA.debugLineNum = 1543;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1544;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==21 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0) { 
 //BA.debugLineNum = 1546;BA.debugLine="temp = (Q1)/(2+b3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2+_vvvvvvvvvvv1+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1547;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1548;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==21 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 1551;BA.debugLine="temp = (Q1)/(1+b3k+a3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv1+_vvvvvvvvvvv0));
 //BA.debugLineNum = 1552;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1553;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==21 && ((_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==1)) && _vvvvvvv1==1 && _vvvvvv7[(int) (17)]==0) { 
 //BA.debugLineNum = 1556;BA.debugLine="temp = (Q1)/(1+b3k+b3b)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(1+_vvvvvvvvvvv1+_vvvvvvvvvvv2));
 //BA.debugLineNum = 1557;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1558;BA.debugLine="Return";
if (true) return "";
 }else if((_vvvvvvvvv1==12 || _vvvvvvvvv1==13 || (_vvvvvv7[(int) (15)]==0 && _vvvvvvvvv1==18) || (_vvvvvvvvv1==19 && _vvvvvv7[(int) (16)]==0)) && (_vvvvvv7[(int) (13)]==1 || _vvvvvv7[(int) (14)]==1 || (_vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (21)]==1) || (_vvvvvv7[(int) (22)]==1 && _vvvvvv7[(int) (14)]==0))) { 
 //BA.debugLineNum = 1564;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 1565;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1568;BA.debugLine="temp = (2*Q1)/(L3+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+3));
 //BA.debugLineNum = 1569;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1570;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1573;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1574;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1575;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1578;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+3));
 //BA.debugLineNum = 1579;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1580;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1583;BA.debugLine="temp = (2*Q1)/(2*b1b+b1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1584;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1585;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1588;BA.debugLine="temp = (2*Q1)/(L3+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+2));
 //BA.debugLineNum = 1589;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1590;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1593;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1594;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1595;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1598;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+2));
 //BA.debugLineNum = 1599;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1600;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1604;BA.debugLine="temp = (2*Q1)/(2*b1b+b1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1605;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1606;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1610;BA.debugLine="temp = (Q1)/(L3+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+3));
 //BA.debugLineNum = 1611;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1612;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1615;BA.debugLine="temp = (Q1)/(2*a1b+b1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1616;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1617;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1620;BA.debugLine="temp = (Q1)/(2*b1b+a1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+3));
 //BA.debugLineNum = 1621;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1622;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1625;BA.debugLine="temp = (Q1)/(2*b1b+b1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1626;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1627;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1630;BA.debugLine="temp = (Q1)/(L3+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+1));
 //BA.debugLineNum = 1631;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1632;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1635;BA.debugLine="temp = (Q1)/(2*a1b+b1k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+1));
 //BA.debugLineNum = 1636;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1637;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1640;BA.debugLine="temp = (Q1)/(2*b1b+a1k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+1));
 //BA.debugLineNum = 1641;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1642;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1645;BA.debugLine="temp = (Q1)/(2*b1b+b1k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+1));
 //BA.debugLineNum = 1646;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1647;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1650;BA.debugLine="temp = (2*Q1)/(L3+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+3));
 //BA.debugLineNum = 1651;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1652;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1655;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1656;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1657;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1661;BA.debugLine="temp = (2*Q1)/(L3+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+2));
 //BA.debugLineNum = 1662;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1663;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1668;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1669;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1670;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1674;BA.debugLine="temp = (2*Q1)/(L3+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+1));
 //BA.debugLineNum = 1675;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1676;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==10 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1680;BA.debugLine="temp = (2*Q1)/(2*a1b+b1k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+1));
 //BA.debugLineNum = 1681;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1682;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==11 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1687;BA.debugLine="temp = (Q1)/(L3+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+3));
 //BA.debugLineNum = 1688;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1689;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==11 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0) { 
 //BA.debugLineNum = 1693;BA.debugLine="temp = (Q1)/(2*b1b+a1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+3));
 //BA.debugLineNum = 1694;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1695;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==11 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 1700;BA.debugLine="temp = (Q1)/(L3+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvvvv6+3));
 //BA.debugLineNum = 1701;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1702;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==11 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0) { 
 //BA.debugLineNum = 1706;BA.debugLine="temp = (Q1)/(2*b1b+a1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+3));
 //BA.debugLineNum = 1707;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1708;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1711;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+3));
 //BA.debugLineNum = 1712;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1713;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1716;BA.debugLine="temp = (2*Q1)/(2*b1b+b1k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1717;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1718;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1722;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+2));
 //BA.debugLineNum = 1723;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1724;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1728;BA.debugLine="temp = (2*Q1)/(2*b1b+b1k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1729;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1730;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==16 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 1735;BA.debugLine="temp = (2*Q1)/(2*b1b+a1k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvvv3+1));
 //BA.debugLineNum = 1736;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1737;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1741;BA.debugLine="temp = (Q1)/(2*a1b+b1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1742;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1743;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1747;BA.debugLine="temp = (Q1)/(2*b1b+b1k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+3));
 //BA.debugLineNum = 1748;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1749;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1753;BA.debugLine="temp = (Q1)/(2*a1b+b1k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1754;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1755;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1759;BA.debugLine="temp = (Q1)/(2*b1b+b1k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+2));
 //BA.debugLineNum = 1760;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1761;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==1 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1765;BA.debugLine="temp = (Q1)/(2*a1b+b1k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv4+_vvvvvvvvvvv5+1));
 //BA.debugLineNum = 1766;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1767;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==17 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0) { 
 //BA.debugLineNum = 1771;BA.debugLine="temp = (Q1)/(2*b1b+b1k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv6+_vvvvvvvvvvv5+1));
 //BA.debugLineNum = 1772;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1773;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1776;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1777;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1778;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1782;BA.debugLine="temp = (2*Q1)/(2*a2b+b2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1783;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1784;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1788;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1789;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1790;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1794;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1795;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1796;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1800;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1801;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1802;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1806;BA.debugLine="temp = (2*Q1)/(2*a2b+b2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1807;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1808;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1811;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1812;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1813;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==8 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1816;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1817;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1818;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1821;BA.debugLine="temp = (Q1)/(2*a2b+a2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1822;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1823;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1826;BA.debugLine="temp = (Q1)/(2*a2b+b2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1827;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1828;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1831;BA.debugLine="temp = (Q1)/(2*b2b+a2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1832;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1833;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1836;BA.debugLine="temp = (Q1)/(2*b2b+b2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1837;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1838;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1842;BA.debugLine="temp = (Q1)/(2*a2b+a2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1843;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1844;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1847;BA.debugLine="temp = (Q1)/(2*a2b+b2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+1));
 //BA.debugLineNum = 1848;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1849;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1852;BA.debugLine="temp = (Q1)/(2*b2b+a2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1853;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1854;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==9 && _vvvvvv7[(int) (11)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1857;BA.debugLine="temp = (Q1)/(2*b2b+b2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+1));
 //BA.debugLineNum = 1858;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1859;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1864;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1865;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1866;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1869;BA.debugLine="temp = (2*Q1)/(2*a2b+b2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1870;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1871;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1874;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1875;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1876;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1879;BA.debugLine="temp = (2*Q1)/(2*a2b+b2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1880;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1881;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1884;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1885;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1886;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==12 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1889;BA.debugLine="temp = (2*Q1)/(2*a2b+a2k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1890;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1891;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1894;BA.debugLine="temp = (Q1)/(2*a2b+a2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1895;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1896;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0) { 
 //BA.debugLineNum = 1899;BA.debugLine="temp = (Q1)/(2*b2b+a2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1900;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1901;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1906;BA.debugLine="temp = (Q1)/(2*a2b+a2k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1907;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1908;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0) { 
 //BA.debugLineNum = 1912;BA.debugLine="temp = (Q1)/(2*b2b+a2k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1913;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1914;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 1919;BA.debugLine="temp = (Q1)/(2*a2b+a2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1920;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1921;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==13 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0) { 
 //BA.debugLineNum = 1924;BA.debugLine="temp = (Q1)/(2*b2b+a2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1925;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1926;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1929;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+3));
 //BA.debugLineNum = 1930;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1931;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1934;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k+3)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1935;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1936;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1940;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+2));
 //BA.debugLineNum = 1941;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1942;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1945;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k+2)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1946;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1947;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 1952;BA.debugLine="temp = (2*Q1)/(2*b2b+a2k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvvv1+1));
 //BA.debugLineNum = 1953;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1954;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==18 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1957;BA.debugLine="temp = (2*Q1)/(2*b2b+b2k+1)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+1));
 //BA.debugLineNum = 1958;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1959;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1962;BA.debugLine="temp = (Q1)/(2*a2b+b2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1963;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1964;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1967;BA.debugLine="temp = (Q1)/(2*b2b+b2k+3)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+3));
 //BA.debugLineNum = 1968;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1969;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1973;BA.debugLine="temp = (Q1)/(2*a2b+b2k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1974;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1975;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1978;BA.debugLine="temp = (Q1)/(2*b2b+b2k+2)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+2));
 //BA.debugLineNum = 1979;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1980;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==0 && _vvvvvv7[(int) (12)]==1 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1983;BA.debugLine="temp = (Q1)/(2*a2b+b2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvvv2+_vvvvvvvvvvv3+1));
 //BA.debugLineNum = 1984;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1985;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvvv1==19 && _vvvvvv7[(int) (11)]==1 && _vvvvvv7[(int) (12)]==0 && _vvvvvvv2==1 && _vvvvvvv3==1 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (16)]==0) { 
 //BA.debugLineNum = 1988;BA.debugLine="temp = (Q1)/(2*b2b+b2k+1)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(2*_vvvvvvvvvvv4+_vvvvvvvvvvv3+1));
 //BA.debugLineNum = 1989;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ار";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 1990;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 1994;BA.debugLine="If isOne(11,16) OR isOne(21,24) Then";
if (_vvvvvvvvvvvvvvvvvvvvvvv5((int) (11),(int) (16)) || _vvvvvvvvvvvvvvvvvvvvvvv5((int) (21),(int) (24))) { 
 //BA.debugLineNum = 1995;BA.debugLine="WNP=1";
_vvvvvvv4 = (int) (1);
 };
 //BA.debugLineNum = 1997;BA.debugLine="If T(19)=0 AND T(20)=0 AND WNP=1 AND ( _ 			(w=1";
if (_vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==0 && _vvvvvvv4==1 && ((_vvvvvvvvv1==14 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvvvvvvv0==1) || (_vvvvvvvvv1==20 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==0 && _vvvvvvvvvvv2==1) || (_vvvvvvvvv1==15 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvvvvvvv7==1) || (_vvvvvvvvv1==21 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvvvvvvv1==1))) { 
 //BA.debugLineNum = 2003;BA.debugLine="temp = Q1/ 6";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)6);
 //BA.debugLineNum = 2004;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2005;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2007;BA.debugLine="If (T(17)=1 AND T(18)=0 AND T(19)=0 AND T(20)=0";
if ((_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==0 && _vvvvvv7[(int) (26)]==0) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==0 && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==0) || (_vvvvvv7[(int) (25)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==0 && _vvvvvv7[(int) (26)]==0) || (_vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (19)]==0 && _vvvvvv7[(int) (20)]==0 && _vvvvvv7[(int) (25)]==0)) { 
 //BA.debugLineNum = 2011;BA.debugLine="CLQ=1";
_vvvvvvv5 = (int) (1);
 };
 //BA.debugLineNum = 2014;BA.debugLine="If T(11)=1 Then sum=sum+2";
if (_vvvvvv7[(int) (11)]==1) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+2);};
 //BA.debugLineNum = 2015;BA.debugLine="If T(12)=1 Then sum=sum+1";
if (_vvvvvv7[(int) (12)]==1) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+1);};
 //BA.debugLineNum = 2016;BA.debugLine="If T(13)=1 Then sum=sum+(2*a1b)";
if (_vvvvvv7[(int) (13)]==1) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(2*_vvvvvvvvvvvv4));};
 //BA.debugLineNum = 2017;BA.debugLine="If T(14)=1 Then sum=sum+a1k";
if (_vvvvvv7[(int) (14)]==1) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+_vvvvvvvvvvvv3);};
 //BA.debugLineNum = 2018;BA.debugLine="If (T(15)=1 AND T(13)=0 AND T(14)=0 AND T(20)=0";
if ((_vvvvvv7[(int) (15)]==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (20)]==0 && _vvvvvv7[(int) (21)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(2*_vvvvvvvvvvvv2));};
 //BA.debugLineNum = 2019;BA.debugLine="If (T(16)=1 AND T(13)=0 AND T(14)=0 AND T(21)=0";
if ((_vvvvvv7[(int) (16)]==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (22)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(_vvvvvvvvvvvv1));};
 //BA.debugLineNum = 2020;BA.debugLine="If (T(21)=1 AND T(13)=0)  Then sum=sum+(2*b1b)";
if ((_vvvvvv7[(int) (21)]==1 && _vvvvvv7[(int) (13)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(2*_vvvvvvvvvvv6));};
 //BA.debugLineNum = 2021;BA.debugLine="If (T(22)=1 AND T(14)=0 ) Then sum=sum+(b1k)";
if ((_vvvvvv7[(int) (22)]==1 && _vvvvvv7[(int) (14)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(_vvvvvvvvvvv5));};
 //BA.debugLineNum = 2022;BA.debugLine="If (T(23)=1 AND T(13)=0 AND T(14)=0 AND T(15)=0";
if ((_vvvvvv7[(int) (23)]==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (15)]==0 && _vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (22)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(2*_vvvvvvvvvvv4));};
 //BA.debugLineNum = 2023;BA.debugLine="If (T(24)=1 AND T(13)=0 AND T(14)=0 AND T(16)=0";
if ((_vvvvvv7[(int) (24)]==1 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (16)]==0 && _vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (22)]==0)) { 
_vvvvvvvvv6 = (float) (_vvvvvvvvv6+(2*_vvvvvvvvvvv3));};
 //BA.debugLineNum = 2025;BA.debugLine="If (WNP=1 AND CLQ=1) AND (w=9 OR w=11 OR (T(14)=";
if ((_vvvvvvv4==1 && _vvvvvvv5==1) && (_vvvvvvvvv1==9 || _vvvvvvvvv1==11 || (_vvvvvv7[(int) (14)]==0 && _vvvvvvvvv1==17) || (_vvvvvvvvv1==13 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (22)]==0) || (_vvvvvvvvv1==19 && _vvvvvv7[(int) (13)]==0 && _vvvvvv7[(int) (14)]==0 && _vvvvvv7[(int) (21)]==0 && _vvvvvv7[(int) (22)]==0 && _vvvvvv7[(int) (16)]==0))) { 
 //BA.debugLineNum = 2026;BA.debugLine="temp = (5*Q1)/(6*sum)";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*_vvvvvvvvv6));
 //BA.debugLineNum = 2027;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2028;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2030;BA.debugLine="If T(19)=1 OR T(20)=1 OR (a3b>1 AND T(17)=1) OR";
if (_vvvvvv7[(int) (19)]==1 || _vvvvvv7[(int) (20)]==1 || (_vvvvvvvvvvv0>1 && _vvvvvv7[(int) (17)]==1) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (19)]==1) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (20)]==1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (25)]==1 && _vvvvvvvvvvv2>1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (25)]==1) || (_vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (20)]==1 && _vvvvvv7[(int) (25)]==1) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (18)]==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (25)]==1 && _vvvvvv7[(int) (17)]==0) || (_vvvvvv7[(int) (25)]==1 && _vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (17)]==0 && _vvvvvv7[(int) (18)]==0) || (_vvvvvvvvvvv7>1 && _vvvvvv7[(int) (18)]==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (19)]==1) || (_vvvvvv7[(int) (18)]==1 && _vvvvvv7[(int) (20)]==1) || (_vvvvvv7[(int) (18)]==0 && _vvvvvv7[(int) (26)]==1 && _vvvvvvvvvvv1>1) || (_vvvvvv7[(int) (19)]==1 && _vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (18)]==0) || (_vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (20)]==1 && _vvvvvv7[(int) (18)]==0) || (_vvvvvv7[(int) (17)]==1 && _vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (18)]==0)) { 
 //BA.debugLineNum = 2035;BA.debugLine="DON=1";
_vvvvvvv6 = (int) (1);
 };
 //BA.debugLineNum = 2037;BA.debugLine="If T(19)=1 Then xsum=xsum+1";
if (_vvvvvv7[(int) (19)]==1) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+1);};
 //BA.debugLineNum = 2038;BA.debugLine="If T(20)=1 Then xsum=xsum+1";
if (_vvvvvv7[(int) (20)]==1) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+1);};
 //BA.debugLineNum = 2039;BA.debugLine="If T(17)=1 Then xsum=xsum+(a3b)";
if (_vvvvvv7[(int) (17)]==1) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+(_vvvvvvvvvvv0));};
 //BA.debugLineNum = 2040;BA.debugLine="If T(18)=1 Then xsum=xsum+(a3k)";
if (_vvvvvv7[(int) (18)]==1) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+(_vvvvvvvvvvv7));};
 //BA.debugLineNum = 2041;BA.debugLine="If T(25)=1 AND T(17)=0 Then xsum=xsum+(b3b)";
if (_vvvvvv7[(int) (25)]==1 && _vvvvvv7[(int) (17)]==0) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+(_vvvvvvvvvvv2));};
 //BA.debugLineNum = 2042;BA.debugLine="If T(26)=1 AND T(18)=0 Then xsum=xsum+(b3k)";
if (_vvvvvv7[(int) (26)]==1 && _vvvvvv7[(int) (18)]==0) { 
_vvvvvvvvv7 = (float) (_vvvvvvvvv7+(_vvvvvvvvvvv1));};
 //BA.debugLineNum = 2044;BA.debugLine="If (WNP=1 AND DON=1) AND (w=81 OR w=91 OR w=14 O";
if ((_vvvvvvv4==1 && _vvvvvvv6==1) && (_vvvvvvvvv1==81 || _vvvvvvvvv1==91 || _vvvvvvvvv1==14 || _vvvvvvvvv1==15 || (_vvvvvvvvv1==20 && _vvvvvv7[(int) (17)]==0) || (_vvvvvvvvv1==21 && _vvvvvv7[(int) (18)]==0))) { 
 //BA.debugLineNum = 2045;BA.debugLine="temp = Q1/(3*xsum)";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)(3*_vvvvvvvvv7));
 //BA.debugLineNum = 2046;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2047;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2049;BA.debugLine="If (WNP=1 AND DON=1) AND (w=8 OR w=10 OR(w=16 AN";
if ((_vvvvvvv4==1 && _vvvvvvv6==1) && (_vvvvvvvvv1==8 || _vvvvvvvvv1==10 || (_vvvvvvvvv1==16 && _vvvvvv7[(int) (13)]==0) || (_vvvvvvvvv1==12 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22))) || (_vvvvvvvvv1==18 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (15)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22))))) { 
 //BA.debugLineNum = 2050;BA.debugLine="temp = (4*Q1)/(3*xsum)";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*_vvvvvvvvv7));
 //BA.debugLineNum = 2051;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2052;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2054;BA.debugLine="If (WNP=1 AND DON=1) AND (w=9 OR w=11 OR(w=17 AN";
if ((_vvvvvvv4==1 && _vvvvvvv6==1) && (_vvvvvvvvv1==9 || _vvvvvvvvv1==11 || (_vvvvvvvvv1==17 && _vvvvvv7[(int) (14)]==0) || (_vvvvvvvvv1==13 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22))) || (_vvvvvvvvv1==19 && _vvvvvv7[(int) (16)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (13),(int) (14)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (21),(int) (22))))) { 
 //BA.debugLineNum = 2055;BA.debugLine="temp = (2*Q1)/(3*xsum)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*_vvvvvvvvv7));
 //BA.debugLineNum = 2056;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2057;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2062;BA.debugLine="Label2.Text = \"حالت استثنا! شما می توانید با توسع";
mostCurrent._label2.setText((Object)("حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"));
 //BA.debugLineNum = 2063;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 2064;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvvv6() throws Exception{
 //BA.debugLineNum = 2066;BA.debugLine="Sub calc3";
 //BA.debugLineNum = 2067;BA.debugLine="If isZero(34,39) AND isZero(46,51) Then M2=1";
if (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (39)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (51))) { 
_vvvvvvvv2 = (int) (1);};
 //BA.debugLineNum = 2068;BA.debugLine="If isZero(28,33) AND isZero(40,45) Then M1=1";
if (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (33)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (45))) { 
_vvvvvvvv1 = (int) (1);};
 //BA.debugLineNum = 2070;BA.debugLine="If (w=24 OR w=25 OR (T(30)=0 AND w=36) OR (T(31)=";
if ((_vvvvvvvvv1==24 || _vvvvvvvvv1==25 || (_vvvvvv7[(int) (30)]==0 && _vvvvvvvvv1==36) || (_vvvvvv7[(int) (31)]==0 && _vvvvvvvvv1==37)) && (_vvvvvv7[(int) (28)]==1 || _vvvvvv7[(int) (29)]==1 || _vvvvvv7[(int) (40)]==1 || _vvvvvv7[(int) (41)]==1)) { 
 //BA.debugLineNum = 2072;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 2073;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2076;BA.debugLine="If ( w=30 OR w=31 OR ( T(36)=0 AND w=42 ) OR (T(3";
if ((_vvvvvvvvv1==30 || _vvvvvvvvv1==31 || (_vvvvvv7[(int) (36)]==0 && _vvvvvvvvv1==42) || (_vvvvvv7[(int) (37)]==0 && _vvvvvvvvv1==43)) && (_vvvvvv7[(int) (34)]==1 || _vvvvvv7[(int) (35)]==1 || _vvvvvv7[(int) (46)]==1 || _vvvvvv7[(int) (47)]==1)) { 
 //BA.debugLineNum = 2078;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 2079;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2082;BA.debugLine="If T(28)=1 Then WSUM=WSUM+ 2*c22";
if (_vvvvvv7[(int) (28)]==1) { 
_vvvvvvvv3 = (int) (_vvvvvvvv3+2*_vvvvvvvvvvvv5);};
 //BA.debugLineNum = 2083;BA.debugLine="If T(29)=1 Then WSUM=WSUM+ c23";
if (_vvvvvv7[(int) (29)]==1) { 
_vvvvvvvv3 = (int) (_vvvvvvvv3+_vvvvvvvvvvvv6);};
 //BA.debugLineNum = 2084;BA.debugLine="If T(28)=1 AND T(40)=1 Then WSUM=WSUM+2*c34";
if (_vvvvvv7[(int) (28)]==1 && _vvvvvv7[(int) (40)]==1) { 
_vvvvvvvv3 = (int) (_vvvvvvvv3+2*_vvvvvvvvvvvvvv1);};
 //BA.debugLineNum = 2085;BA.debugLine="If T(29)=0 AND T(41)=1 Then WSUM=WSUM+c35";
if (_vvvvvv7[(int) (29)]==0 && _vvvvvv7[(int) (41)]==1) { 
_vvvvvvvv3 = (int) (_vvvvvvvv3+_vvvvvvvvvvvvvv2);};
 //BA.debugLineNum = 2086;BA.debugLine="If T(28)=1 AND T(40)=1 Then WSUM=WSUM+2*c34";
if (_vvvvvv7[(int) (28)]==1 && _vvvvvv7[(int) (40)]==1) { 
_vvvvvvvv3 = (int) (_vvvvvvvv3+2*_vvvvvvvvvvvvvv1);};
 //BA.debugLineNum = 2087;BA.debugLine="If ( ( isZero(28,29) AND isZero(40,41) ) AND T(30";
if (((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) && _vvvvvv7[(int) (30)]==1)) { 
_vvvvvvvv4 = (int) (_vvvvvvvv4+2*_vvvvvvvvvvvv7);};
 //BA.debugLineNum = 2088;BA.debugLine="If ( ( isZero(28,29) AND isZero(40,41) ) AND T(30";
if (((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) && _vvvvvv7[(int) (30)]==0 && _vvvvvv7[(int) (42)]==1)) { 
_vvvvvvvv4 = (int) (_vvvvvvvv4+2*_vvvvvvvvvvvvvv3);};
 //BA.debugLineNum = 2089;BA.debugLine="If ( ( isZero(28,29) AND isZero(40,41) ) AND T(31";
if (((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) && _vvvvvv7[(int) (31)]==1)) { 
_vvvvvvvv4 = (int) (_vvvvvvvv4+_vvvvvvvvvvvv0);};
 //BA.debugLineNum = 2090;BA.debugLine="If ( ( isZero(28,29) AND isZero(40,41) ) AND T(31";
if (((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) && _vvvvvv7[(int) (31)]==0 && _vvvvvv7[(int) (43)]==1)) { 
_vvvvvvvv4 = (int) (_vvvvvvvv4+_vvvvvvvvvvvvvv4);};
 //BA.debugLineNum = 2092;BA.debugLine="If ( isZero(28,29) AND isZero(40,41) )  Then PP=2";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41)))) { 
_vvvvvvvv5 = (int) (2);};
 //BA.debugLineNum = 2093;BA.debugLine="If ( isZero(32,33) AND isZero(44,45) ) AND ( POW=";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (32),(int) (33)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (44),(int) (45))) && (_vvvvvvv7==1 && _vvvvvv7[(int) (27)]==1) && (_vvvvvvvvv1==22 || (_vvvvvvvvv1==34 && _vvvvvv7[(int) (28)]==0) || (_vvvvvvvvv1==24 && _vvvvvvvv5==2) || (_vvvvvvvvv1==36 && _vvvvvv7[(int) (42)]==1 && _vvvvvv7[(int) (30)]==0 && _vvvvvvvv5==2))) { 
 //BA.debugLineNum = 2095;BA.debugLine="If M2<>1 Then";
if (_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2096;BA.debugLine="temp = (4*Q1)/(3*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2097;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2098;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2100;BA.debugLine="temp = (2*Q1)/(WSUM+WPSUM)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(_vvvvvvvv3+_vvvvvvvv4));
 //BA.debugLineNum = 2101;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2102;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2107;BA.debugLine="If (T(27)=1 AND POW=1) AND (isZero(32,33) AND isZ";
if ((_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (32),(int) (33)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (44),(int) (45))) && (_vvvvvvvvv1==23 || (_vvvvvv7[(int) (29)]==0 && _vvvvvvvvv1==35) || (_vvvvvvvvv1==25 && _vvvvvvvv5==2) || (_vvvvvvvvv1==37 && _vvvvvvvv5==2 && _vvvvvv7[(int) (31)]==0 && _vvvvvv7[(int) (43)]==1))) { 
 //BA.debugLineNum = 2110;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2111;BA.debugLine="temp = (Q1)/(WSUM+WPSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvv3+_vvvvvvvv4));
 //BA.debugLineNum = 2112;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2113;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2115;BA.debugLine="temp = (2*Q1)/(3*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2116;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2117;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2122;BA.debugLine="If T(32)=1 Then MSUM=MSUM+c26";
if (_vvvvvv7[(int) (32)]==1) { 
_vvvvvvvv6 = (int) (_vvvvvvvv6+_vvvvvvvvvvvvv1);};
 //BA.debugLineNum = 2123;BA.debugLine="If T(33)=1 Then MSUM=MSUM+c27";
if (_vvvvvv7[(int) (33)]==1) { 
_vvvvvvvv6 = (int) (_vvvvvvvv6+_vvvvvvvvvvvvv2);};
 //BA.debugLineNum = 2124;BA.debugLine="If (T(32)=0 AND T(44)=1)Then MSUM=MSUM+c38";
if ((_vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==1)) { 
_vvvvvvvv6 = (int) (_vvvvvvvv6+_vvvvvvvvvvvvvv5);};
 //BA.debugLineNum = 2125;BA.debugLine="If (T(33)=0 AND T(45)=1) Then MSUM=MSUM+c39";
if ((_vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==1)) { 
_vvvvvvvv6 = (int) (_vvvvvvvv6+_vvvvvvvvvvvvvv6);};
 //BA.debugLineNum = 2128;BA.debugLine="If (isZero(28,43) AND (T(27)=1 AND POW=1) ) AND (";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (43)) && (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1)) && (_vvvvvvvvv1==26 || _vvvvvvvvv1==27 || (_vvvvvvvvv1==38 && _vvvvvv7[(int) (32)]==0) || (_vvvvvvvvv1==39 && _vvvvvv7[(int) (33)]==0))) { 
 //BA.debugLineNum = 2129;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2130;BA.debugLine="temp = (Q1)/(MSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvv6));
 //BA.debugLineNum = 2131;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2132;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2134;BA.debugLine="temp = (2*Q1)/(3*MSUM)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*_vvvvvvvv6));
 //BA.debugLineNum = 2135;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2136;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2140;BA.debugLine="If ((T(32)=1 AND c26=1 AND T(33)=0 AND T(45)=0) O";
if (((_vvvvvv7[(int) (32)]==1 && _vvvvvvvvvvvvv1==1 && _vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==0) || (_vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==1 && _vvvvvvvvvvvvvv5==1 && _vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==0) || (_vvvvvv7[(int) (33)]==1 && _vvvvvvvvvvvvv2==1 && _vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==0) || (_vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==1 && _vvvvvvvvvvvvvv6==1 && _vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==0)) && (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) && (_vvvvvv7[(int) (28)]!=0 || _vvvvvv7[(int) (29)]!=0 || _vvvvvv7[(int) (30)]!=0 || (31)!=0 || _vvvvvv7[(int) (40)]!=0 || _vvvvvv7[(int) (41)]!=0 || _vvvvvv7[(int) (42)]!=0 || _vvvvvv7[(int) (43)]!=0)) { 
 //BA.debugLineNum = 2145;BA.debugLine="CK=1";
_vvvvvvv0 = (int) (1);
 };
 //BA.debugLineNum = 2148;BA.debugLine="If CK=1 AND ( w=26 OR w=27 OR (t(32)=0 AND w=38)";
if (_vvvvvvv0==1 && (_vvvvvvvvv1==26 || _vvvvvvvvv1==27 || (_vvvvvv7[(int) (32)]==0 && _vvvvvvvvv1==38) || (_vvvvvv7[(int) (33)]==0 && _vvvvvvvvv1==39))) { 
 //BA.debugLineNum = 2149;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2150;BA.debugLine="temp = (Q1)/6";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 2151;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2152;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2154;BA.debugLine="temp = Q1/9";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)9);
 //BA.debugLineNum = 2155;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2156;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2162;BA.debugLine="If CK=1 AND (w=22 OR (t(28)=0 AND w=34)  OR _ 		(";
if (_vvvvvvv0==1 && (_vvvvvvvvv1==22 || (_vvvvvv7[(int) (28)]==0 && _vvvvvvvvv1==34) || (_vvvvvvvvv1==34 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (30)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41)) && _vvvvvvvvv1==36))) { 
 //BA.debugLineNum = 2164;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2165;BA.debugLine="temp = (5*Q1)/(3*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(3*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2166;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2167;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2169;BA.debugLine="temp = (10*Q1)/(9*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((10*_vvvvvvvvv3)/(double)(9*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2170;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2171;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2175;BA.debugLine="If CK=1 AND (w=23 OR (t(29)=0 AND w=35)OR (isZero";
if (_vvvvvvv0==1 && (_vvvvvvvvv1==23 || (_vvvvvv7[(int) (29)]==0 && _vvvvvvvvv1==35) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41)) && _vvvvvvvvv1==25)) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41)) && _vvvvvvvvv1==37 && _vvvvvv7[(int) (31)]==0)) { 
 //BA.debugLineNum = 2176;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2177;BA.debugLine="temp = (5*Q1)/ (6*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2178;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2179;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2181;BA.debugLine="temp = (5*Q1)/(9*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(9*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2182;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2183;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2187;BA.debugLine="If ( (T(32)=1 AND c26<1) OR (T(32)=0 AND T(44)=1";
if (((_vvvvvv7[(int) (32)]==1 && _vvvvvvvvvvvvv1<1) || (_vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==1 && _vvvvvvvvvvvvvv5>1) || (_vvvvvvvvvvvvv2>1 && _vvvvvv7[(int) (33)]==1) || (_vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==1 && _vvvvvvvvvvvvvv6>1) || (_vvvvvv7[(int) (32)]==1 && _vvvvvv7[(int) (33)]==1) || (_vvvvvv7[(int) (32)]==1 && _vvvvvv7[(int) (33)]==0 && _vvvvvv7[(int) (45)]==1) || (_vvvvvv7[(int) (32)]==0 && _vvvvvv7[(int) (44)]==1 && _vvvvvv7[(int) (33)]==1) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (32),(int) (33)) && _vvvvvv7[(int) (44)]==1 && _vvvvvv7[(int) (45)]==1)) && (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) && (_vvvvvv7[(int) (28)]!=0 || _vvvvvv7[(int) (29)]!=0 || _vvvvvv7[(int) (30)]!=0 || (31)!=0 || _vvvvvv7[(int) (40)]!=0 || _vvvvvv7[(int) (41)]!=0 || _vvvvvv7[(int) (42)]!=0 || _vvvvvv7[(int) (43)]!=0)) { 
 //BA.debugLineNum = 2192;BA.debugLine="CK=2";
_vvvvvvv0 = (int) (2);
 };
 //BA.debugLineNum = 2194;BA.debugLine="If CK=2 AND ( w=26 OR w=27 OR (w=38 AND T(32)=0)";
if (_vvvvvvv0==2 && (_vvvvvvvvv1==26 || _vvvvvvvvv1==27 || (_vvvvvvvvv1==38 && _vvvvvv7[(int) (32)]==0) || (_vvvvvvvvv1==39 && _vvvvvv7[(int) (33)]==0))) { 
 //BA.debugLineNum = 2195;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2196;BA.debugLine="temp = (Q1)/ (3*MSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*_vvvvvvvv6));
 //BA.debugLineNum = 2197;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2198;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2200;BA.debugLine="temp = (2*Q1)/(9*MSUM)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(9*_vvvvvvvv6));
 //BA.debugLineNum = 2201;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2202;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2205;BA.debugLine="If CK=2 AND ( w=22 OR (w=34 AND T(28)=0) OR (w=24";
if (_vvvvvvv0==2 && (_vvvvvvvvv1==22 || (_vvvvvvvvv1==34 && _vvvvvv7[(int) (28)]==0) || (_vvvvvvvvv1==24 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) || (_vvvvvvvvv1==36 && _vvvvvv7[(int) (30)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))))) { 
 //BA.debugLineNum = 2206;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2207;BA.debugLine="temp = (4*Q1)/ (3*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(3*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2208;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2209;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2211;BA.debugLine="temp = (8*Q1)/(9*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((8*_vvvvvvvvv3)/(double)(9*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2212;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2213;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2217;BA.debugLine="If CK=2 AND ( w=23 OR (w=35 AND T(29)=0) OR (w=25";
if (_vvvvvvv0==2 && (_vvvvvvvvv1==23 || (_vvvvvvvvv1==35 && _vvvvvv7[(int) (29)]==0) || (_vvvvvvvvv1==25 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))) || (_vvvvvvvvv1==37 && _vvvvvv7[(int) (31)]==0 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (28),(int) (29)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (40),(int) (41))))) { 
 //BA.debugLineNum = 2218;BA.debugLine="If M2=1 Then";
if (_vvvvvvvv2==1) { 
 //BA.debugLineNum = 2219;BA.debugLine="temp = (2*Q1)/ (3*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2220;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2221;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv2!=1) { 
 //BA.debugLineNum = 2223;BA.debugLine="temp = (4*Q1)/ (9*(WSUM+WPSUM))";
_vvvvvvvvv5 = (float) ((4*_vvvvvvvvv3)/(double)(9*(_vvvvvvvv3+_vvvvvvvv4)));
 //BA.debugLineNum = 2224;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2225;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2228;BA.debugLine="If (w=30 OR w=31 OR (w=42 AND T(36)=0) OR(w=43 AN";
if ((_vvvvvvvvv1==30 || _vvvvvvvvv1==31 || (_vvvvvvvvv1==42 && _vvvvvv7[(int) (36)]==0) || (_vvvvvvvvv1==43 && _vvvvvv7[(int) (37)]==0)) && (_vvvvvv7[(int) (34)]==1 || _vvvvvv7[(int) (35)]==1 || _vvvvvv7[(int) (46)]==1 || _vvvvvv7[(int) (47)]==1)) { 
 //BA.debugLineNum = 2230;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 2231;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2233;BA.debugLine="If T(34)=1 Then KSUM=KSUM+c28";
if (_vvvvvv7[(int) (34)]==1) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvv3);};
 //BA.debugLineNum = 2234;BA.debugLine="If T(35)=1 Then KSUM=KSUM+c29";
if (_vvvvvv7[(int) (35)]==1) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvv4);};
 //BA.debugLineNum = 2235;BA.debugLine="If (T(34)=0 AND T(46)=1) Then KSUM=KSUM+c40";
if ((_vvvvvv7[(int) (34)]==0 && _vvvvvv7[(int) (46)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvvv7);};
 //BA.debugLineNum = 2236;BA.debugLine="If (T(35)=0 AND T(47)=1) Then KSUM=KSUM+c41";
if ((_vvvvvv7[(int) (35)]==0 && _vvvvvv7[(int) (47)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvvv0);};
 //BA.debugLineNum = 2237;BA.debugLine="If (isZero(34,35) AND isZero(46,47) AND T(36)=1 )";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvv7[(int) (36)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvv5);};
 //BA.debugLineNum = 2238;BA.debugLine="If (isZero(34,35) AND isZero(46,47) AND T(37)=1 )";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvv7[(int) (37)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvv6);};
 //BA.debugLineNum = 2239;BA.debugLine="If (isZero(34,35) AND isZero(46,47) AND T(36)=0 A";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvv7[(int) (36)]==0 && _vvvvvv7[(int) (48)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvvvv1);};
 //BA.debugLineNum = 2240;BA.debugLine="If (isZero(34,35) AND isZero(46,47) AND T(37)=0 A";
if ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvv7[(int) (37)]==0 && _vvvvvv7[(int) (49)]==1)) { 
_vvvvvvvv7 = (int) (_vvvvvvvv7+_vvvvvvvvvvvvvvv2);};
 //BA.debugLineNum = 2242;BA.debugLine="If (T(27)=1 AND POW=1 AND isZero(38,39) AND isZer";
if ((_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1 && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (38),(int) (39)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (50),(int) (51))) && (_vvvvvvvvv1==28 || (_vvvvvvvvv1==40 && _vvvvvv7[(int) (46)]==1) || _vvvvvvvvv1==29 || (_vvvvvvvvv1==41 && _vvvvvv7[(int) (47)]==1) || ((_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47))) && (_vvvvvvvvv1==30 || _vvvvvvvvv1==31 || (_vvvvvvvvv1==42 && _vvvvvv7[(int) (36)]==0) || (_vvvvvvvvv1==43 && _vvvvvv7[(int) (37)]==0))))) { 
 //BA.debugLineNum = 2245;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2246;BA.debugLine="temp = (Q1)/ (KSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvv7));
 //BA.debugLineNum = 2247;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2248;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2250;BA.debugLine="temp = (Q1)/ (3*(KSUM))";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*(_vvvvvvvv7)));
 //BA.debugLineNum = 2251;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2252;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2255;BA.debugLine="If T(38)=1 Then KPSUM=KPSUM+c32";
if (_vvvvvv7[(int) (38)]==1) { 
_vvvvvvvv0 = (int) (_vvvvvvvv0+_vvvvvvvvvvvvv7);};
 //BA.debugLineNum = 2256;BA.debugLine="If T(39)=1 Then KPSUM=KPSUM+c33";
if (_vvvvvv7[(int) (39)]==1) { 
_vvvvvvvv0 = (int) (_vvvvvvvv0+_vvvvvvvvvvvvv0);};
 //BA.debugLineNum = 2257;BA.debugLine="If (T(38)=0 AND T(50)=1) Then KPSUM=KPSUM+c44";
if ((_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==1)) { 
_vvvvvvvv0 = (int) (_vvvvvvvv0+_vvvvvvvvvvvvvvv3);};
 //BA.debugLineNum = 2258;BA.debugLine="If (T(39)=0 AND T(51)=1) Then KPSUM=KPSUM+c45";
if ((_vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==1)) { 
_vvvvvvvv0 = (int) (_vvvvvvvv0+_vvvvvvvvvvvvvvv4);};
 //BA.debugLineNum = 2260;BA.debugLine="If (POW=1 AND T(27)=1) AND (isZero(34,37) AND isZ";
if ((_vvvvvvv7==1 && _vvvvvv7[(int) (27)]==1) && (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (37)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (49))) && (_vvvvvvvvv1==32 || _vvvvvvvvv1==33 || (_vvvvvvvvv1==44 && _vvvvvv7[(int) (38)]==0) || (_vvvvvv7[(int) (39)]==0 && _vvvvvvvvv1==45))) { 
 //BA.debugLineNum = 2262;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2263;BA.debugLine="temp = (Q1)/ (KPSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(_vvvvvvvv0));
 //BA.debugLineNum = 2264;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2265;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2267;BA.debugLine="temp = (Q1)/ (3*KPSUM)";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)(3*_vvvvvvvv0));
 //BA.debugLineNum = 2268;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2269;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2273;BA.debugLine="If ( (T(38)=1 AND c32=1 AND T(39)=0 AND T(51)=0)";
if (((_vvvvvv7[(int) (38)]==1 && _vvvvvvvvvvvvv7==1 && _vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==0) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==1 && _vvvvvvvvvvvvvvv3==1 && _vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==0) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==0 && _vvvvvv7[(int) (39)]==1 && _vvvvvvvvvvvvv0==1) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==0 && _vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==1 && _vvvvvvvvvvvvvvv4==1)) && (_vvvvvvv7==1 && _vvvvvv7[(int) (27)]==1) && (_vvvvvvvvvvvvvvvvvvvvvvv5((int) (34),(int) (37)) || _vvvvvvvvvvvvvvvvvvvvvvv5((int) (46),(int) (49)))) { 
 //BA.debugLineNum = 2278;BA.debugLine="CK=3";
_vvvvvvv0 = (int) (3);
 };
 //BA.debugLineNum = 2281;BA.debugLine="If CK=3 AND ( w=32 OR w=33 OR (t(38)=0 AND w=44)";
if (_vvvvvvv0==3 && (_vvvvvvvvv1==32 || _vvvvvvvvv1==33 || (_vvvvvv7[(int) (38)]==0 && _vvvvvvvvv1==44) || (_vvvvvv7[(int) (39)]==0 && _vvvvvvvvv1==45))) { 
 //BA.debugLineNum = 2282;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2283;BA.debugLine="temp = (Q1)/6";
_vvvvvvvvv5 = (float) ((_vvvvvvvvv3)/(double)6);
 //BA.debugLineNum = 2284;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2285;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2287;BA.debugLine="temp = Q1/18";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)18);
 //BA.debugLineNum = 2288;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2289;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2293;BA.debugLine="If CK=3 AND ( ( w=28 OR (w=40 AND T(34)=0) OR w=2";
if (_vvvvvvv0==3 && ((_vvvvvvvvv1==28 || (_vvvvvvvvv1==40 && _vvvvvv7[(int) (34)]==0) || _vvvvvvvvv1==29 || (_vvvvvvvvv1==41 && _vvvvvv7[(int) (35)]==0)) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==30) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (36)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==42) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==31) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==43))) { 
 //BA.debugLineNum = 2298;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2299;BA.debugLine="temp = (5*Q1)/ (6*KSUM)";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(6*_vvvvvvvv7));
 //BA.debugLineNum = 2300;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2301;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2303;BA.debugLine="temp = (5*Q1)/ (18*KSUM)";
_vvvvvvvvv5 = (float) ((5*_vvvvvvvvv3)/(double)(18*_vvvvvvvv7));
 //BA.debugLineNum = 2304;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2305;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2309;BA.debugLine="If ( (T(38)=1 AND c32>1) OR (T(39)=1 AND c33>1) O";
if (((_vvvvvv7[(int) (38)]==1 && _vvvvvvvvvvvvv7>1) || (_vvvvvv7[(int) (39)]==1 && _vvvvvvvvvvvvv0>1) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==1 && _vvvvvvvvvvvvvvv3>1) || (_vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==1 && _vvvvvvvvvvvvvvv4>1) || (_vvvvvv7[(int) (38)]==1 && _vvvvvv7[(int) (39)]==1) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (50)]==1 && _vvvvvv7[(int) (39)]==1) || (_vvvvvv7[(int) (38)]==1 && _vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (51)]==1) || (_vvvvvv7[(int) (38)]==0 && _vvvvvv7[(int) (39)]==0 && _vvvvvv7[(int) (50)]==1 && _vvvvvv7[(int) (51)]==1)) && (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) && (_vvvvvvvvvvvvvvvvvvvvvvv5((int) (34),(int) (37)) || _vvvvvvvvvvvvvvvvvvvvvvv5((int) (46),(int) (49)))) { 
 //BA.debugLineNum = 2314;BA.debugLine="CK=4";
_vvvvvvv0 = (int) (4);
 };
 //BA.debugLineNum = 2317;BA.debugLine="If CK=4 AND ( w=32 OR w=33 OR (t(38)=0 AND w=44)";
if (_vvvvvvv0==4 && (_vvvvvvvvv1==32 || _vvvvvvvvv1==33 || (_vvvvvv7[(int) (38)]==0 && _vvvvvvvvv1==44) || (_vvvvvv7[(int) (39)]==0 && _vvvvvvvvv1==45))) { 
 //BA.debugLineNum = 2318;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2319;BA.debugLine="temp = Q1/ (3*KPSUM)";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)(3*_vvvvvvvv0));
 //BA.debugLineNum = 2320;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2321;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2323;BA.debugLine="temp = Q1/ (9*KPSUM)";
_vvvvvvvvv5 = (float) (_vvvvvvvvv3/(double)(9*_vvvvvvvv0));
 //BA.debugLineNum = 2324;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2325;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2329;BA.debugLine="If CK=4 AND ( ( w=28 OR (w=40 AND T(34)=0) OR w=2";
if (_vvvvvvv0==4 && ((_vvvvvvvvv1==28 || (_vvvvvvvvv1==40 && _vvvvvv7[(int) (34)]==0) || _vvvvvvvvv1==29 || (_vvvvvvvvv1==41 && _vvvvvv7[(int) (35)]==0)) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==30) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (36)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==42) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==31) || (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (34),(int) (35)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (46),(int) (47)) && _vvvvvvvvv1==43 && _vvvvvv7[(int) (37)]==0))) { 
 //BA.debugLineNum = 2334;BA.debugLine="If M1=1 Then";
if (_vvvvvvvv1==1) { 
 //BA.debugLineNum = 2335;BA.debugLine="temp = (2*Q1)/ (3*KSUM)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(3*_vvvvvvvv7));
 //BA.debugLineNum = 2336;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2337;BA.debugLine="Return";
if (true) return "";
 }else if(_vvvvvvvv1!=1) { 
 //BA.debugLineNum = 2339;BA.debugLine="temp = (2*Q1)/ (9*KSUM)";
_vvvvvvvvv5 = (float) ((2*_vvvvvvvvv3)/(double)(9*_vvvvvvvv7));
 //BA.debugLineNum = 2340;BA.debugLine="Label2.Text = NumberFormat(temp,0,4) & \" درصد ا";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv5,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 2341;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 2346;BA.debugLine="Label2.Text = \"حالت استثنا! شما می توانید با توسع";
mostCurrent._label2.setText((Object)("حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"));
 //BA.debugLineNum = 2347;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 2348;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _vvvvvvvvvvvvvvvvvvvvvvv7(String _text,int _width,int _height) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 155;BA.debugLine="Sub CreateListItem41(Text As String, Width As Int,";
 //BA.debugLineNum = 156;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="p.Initialize(\"p41\")";
_p.Initialize(mostCurrent.activityBA,"p41");
 //BA.debugLineNum = 158;BA.debugLine="p.Color = Colors.Black";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 159;BA.debugLine="p.Tag = \"black\"";
_p.setTag((Object)("black"));
 //BA.debugLineNum = 160;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 161;BA.debugLine="lbl.Initialize(\"\")";
_lbl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 162;BA.debugLine="lbl.Gravity = Bit.OR(Gravity.CENTER_VERTICAL, Gra";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL));
 //BA.debugLineNum = 163;BA.debugLine="lbl.Text = Text";
_lbl.setText((Object)(_text));
 //BA.debugLineNum = 164;BA.debugLine="lbl.TextSize = 20";
_lbl.setTextSize((float) (20));
 //BA.debugLineNum = 165;BA.debugLine="lbl.TextColor = Colors.White";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 166;BA.debugLine="p.AddView(lbl, 0, 0, Width-2dip, Height-2dip) 'vi";
_p.AddView((android.view.View)(_lbl.getObject()),(int) (0),(int) (0),(int) (_width-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (_height-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 167;BA.debugLine="Return p";
if (true) return _p;
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private titleLabel As Label";
mostCurrent._titlelabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim clv41 As CustomListView";
mostCurrent._vvvvvv6 = new com.com7dolphin.heritage.customlistview();
 //BA.debugLineNum = 14;BA.debugLine="Dim T(52) As Int";
_vvvvvv7 = new int[(int) (52)];
;
 //BA.debugLineNum = 15;BA.debugLine="Dim WHP,QMN,CMO,PQN,WNP,CLQ,DON As Int";
_vvvvvv0 = 0;
_vvvvvvv1 = 0;
_vvvvvvv2 = 0;
_vvvvvvv3 = 0;
_vvvvvvv4 = 0;
_vvvvvvv5 = 0;
_vvvvvvv6 = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim POW,CK,M1,M2,WSUM,WPSUM,PP,MSUM,KSUM,KPSUM As";
_vvvvvvv7 = 0;
_vvvvvvv0 = 0;
_vvvvvvvv1 = 0;
_vvvvvvvv2 = 0;
_vvvvvvvv3 = 0;
_vvvvvvvv4 = 0;
_vvvvvvvv5 = 0;
_vvvvvvvv6 = 0;
_vvvvvvvv7 = 0;
_vvvvvvvv0 = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim W As Int";
_vvvvvvvvv1 = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim Q As Float";
_vvvvvvvvv2 = 0f;
 //BA.debugLineNum = 19;BA.debugLine="Dim Q1,Q2,temp,sum,xsum As Float";
_vvvvvvvvv3 = 0f;
_vvvvvvvvv4 = 0f;
_vvvvvvvvv5 = 0f;
_vvvvvvvvv6 = 0f;
_vvvvvvvvv7 = 0f;
 //BA.debugLineNum = 20;BA.debugLine="Dim sbList As List";
mostCurrent._vvvvvvvvv0 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 21;BA.debugLine="Dim Np,Nd,Mp,Md,Z As Int";
_vvvvvvvvvv1 = 0;
_vvvvvvvvvv2 = 0;
_vvvvvvvvvv3 = 0;
_vvvvvvvvvv4 = 0;
_vvvvvvvvvv5 = 0;
 //BA.debugLineNum = 22;BA.debugLine="Dim L3,L4,L5,b3k,b3b,b2k,b2b,b1k,b1b,a3k,a3b,a2k,";
_vvvvvvvvvv6 = 0;
_vvvvvvvvvv7 = 0;
_vvvvvvvvvv0 = 0;
_vvvvvvvvvvv1 = 0;
_vvvvvvvvvvv2 = 0;
_vvvvvvvvvvv3 = 0;
_vvvvvvvvvvv4 = 0;
_vvvvvvvvvvv5 = 0;
_vvvvvvvvvvv6 = 0;
_vvvvvvvvvvv7 = 0;
_vvvvvvvvvvv0 = 0;
_vvvvvvvvvvvv1 = 0;
_vvvvvvvvvvvv2 = 0;
_vvvvvvvvvvvv3 = 0;
_vvvvvvvvvvvv4 = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim c22,c23,c24,c25,c26,c27,c28,c29,c30,c31,c32,c";
_vvvvvvvvvvvv5 = 0;
_vvvvvvvvvvvv6 = 0;
_vvvvvvvvvvvv7 = 0;
_vvvvvvvvvvvv0 = 0;
_vvvvvvvvvvvvv1 = 0;
_vvvvvvvvvvvvv2 = 0;
_vvvvvvvvvvvvv3 = 0;
_vvvvvvvvvvvvv4 = 0;
_vvvvvvvvvvvvv5 = 0;
_vvvvvvvvvvvvv6 = 0;
_vvvvvvvvvvvvv7 = 0;
_vvvvvvvvvvvvv0 = 0;
_vvvvvvvvvvvvvv1 = 0;
_vvvvvvvvvvvvvv2 = 0;
_vvvvvvvvvvvvvv3 = 0;
_vvvvvvvvvvvvvv4 = 0;
_vvvvvvvvvvvvvv5 = 0;
_vvvvvvvvvvvvvv6 = 0;
_vvvvvvvvvvvvvv7 = 0;
_vvvvvvvvvvvvvv0 = 0;
_vvvvvvvvvvvvvvv1 = 0;
_vvvvvvvvvvvvvvv2 = 0;
_vvvvvvvvvvvvvvv3 = 0;
_vvvvvvvvvvvvvvv4 = 0;
 //BA.debugLineNum = 24;BA.debugLine="Private mainPanel As Panel";
mostCurrent._mainpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _homeimg_click() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub homeImg_Click";
 //BA.debugLineNum = 59;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvvvvvvvvvvvvv0(String _msg) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 98;BA.debugLine="Sub inputBox(msg As String) As Int";
 //BA.debugLineNum = 99;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 100;BA.debugLine="Id.InputType = Id.INPUT_TYPE_NUMBERS";
_id.setInputType(_id.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 101;BA.debugLine="Id.Input = \"\"";
_id.setInput("");
 //BA.debugLineNum = 102;BA.debugLine="Id.Hint = \"تعداد پیش فرض 1\"";
_id.setHint("تعداد پیش فرض 1");
 //BA.debugLineNum = 103;BA.debugLine="Id.HintColor = Colors.Gray";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 104;BA.debugLine="Dim ret As Int = Id.show(\"\", msg, \"تایید\", \"انصرا";
_ret = _id.Show("",_msg,"تایید","انصراف","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 105;BA.debugLine="If ret = DialogResponse.POSITIVE Then";
if (_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 106;BA.debugLine="If Id.Input = \"\" Then";
if ((_id.getInput()).equals("")) { 
 //BA.debugLineNum = 107;BA.debugLine="Return 1";
if (true) return (int) (1);
 }else {
 //BA.debugLineNum = 109;BA.debugLine="Return Id.Input";
if (true) return (int)(Double.parseDouble(_id.getInput()));
 };
 }else if(_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 112;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return 0;
}
public static boolean  _vvvvvvvvvvvvvvvvvvvvvvv5(int _a,int _b) throws Exception{
int _i = 0;
 //BA.debugLineNum = 2359;BA.debugLine="Sub isOne(a As Int,b As Int) As Boolean ' 1 or 1 o";
 //BA.debugLineNum = 2360;BA.debugLine="For i=a To b";
{
final int step1968 = 1;
final int limit1968 = _b;
for (_i = _a; (step1968 > 0 && _i <= limit1968) || (step1968 < 0 && _i >= limit1968); _i = ((int)(0 + _i + step1968))) {
 //BA.debugLineNum = 2361;BA.debugLine="If T(i) = 1 Then";
if (_vvvvvv7[_i]==1) { 
 //BA.debugLineNum = 2362;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 2365;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2366;BA.debugLine="End Sub";
return false;
}
public static boolean  _vvvvvvvvvvvvvvvvvvvvvvv4(int _a,int _b) throws Exception{
int _i = 0;
 //BA.debugLineNum = 2350;BA.debugLine="Sub isZero(a As Int,b As Int) As Boolean ' 0 and 0";
 //BA.debugLineNum = 2351;BA.debugLine="For i=a To b";
{
final int step1960 = 1;
final int limit1960 = _b;
for (_i = _a; (step1960 > 0 && _i <= limit1960) || (step1960 < 0 && _i >= limit1960); _i = ((int)(0 + _i + step1960))) {
 //BA.debugLineNum = 2352;BA.debugLine="If T(i) <> 0 Then";
if (_vvvvvv7[_i]!=0) { 
 //BA.debugLineNum = 2353;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 }
};
 //BA.debugLineNum = 2356;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2357;BA.debugLine="End Sub";
return false;
}
public static String  _menuimg_click() throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub menuImg_Click";
 //BA.debugLineNum = 63;BA.debugLine="ToastMessageShow(\"منو غیر فعال است\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("منو غیر فعال است",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _p41_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
int _index = 0;
 //BA.debugLineNum = 170;BA.debugLine="Sub p41_Click";
 //BA.debugLineNum = 171;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 172;BA.debugLine="For i = 0 To clv41.GetSize-1";
{
final int step147 = 1;
final int limit147 = (int) (mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv4()-1);
for (_i = (int) (0); (step147 > 0 && _i <= limit147) || (step147 < 0 && _i >= limit147); _i = ((int)(0 + _i + step147))) {
 //BA.debugLineNum = 173;BA.debugLine="pnl = clv41.GetPanel(i)";
_pnl = mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv3(_i);
 //BA.debugLineNum = 174;BA.debugLine="pnl.Color = Colors.Black";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 175;BA.debugLine="pnl.Tag = \"black\"";
_pnl.setTag((Object)("black"));
 }
};
 //BA.debugLineNum = 177;BA.debugLine="Dim index As Int";
_index = 0;
 //BA.debugLineNum = 178;BA.debugLine="index = clv41.GetItemFromView(Sender)";
_index = (int)(Double.parseDouble(mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv2((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))))));
 //BA.debugLineNum = 179;BA.debugLine="pnl = clv41.GetPanel(index)";
_pnl = mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv3(_index);
 //BA.debugLineNum = 180;BA.debugLine="pnl.Color = Colors.blue";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 181;BA.debugLine="pnl.Tag = \"blue\"";
_pnl.setTag((Object)("blue"));
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvvvv1(String _msg) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
int _i = 0;
anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2 _cd = null;
int _ret = 0;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 127;BA.debugLine="Sub selectBox(msg As String)As String";
 //BA.debugLineNum = 128;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="pnl.Initialize(\"pnl\")";
_pnl.Initialize(mostCurrent.activityBA,"pnl");
 //BA.debugLineNum = 130;BA.debugLine="pnl.Color = Colors.Transparent";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 131;BA.debugLine="clv41.Initialize(Me, \"clv41\")";
mostCurrent._vvvvvv6._initialize(mostCurrent.activityBA,act3.getObject(),"clv41");
 //BA.debugLineNum = 132;BA.debugLine="pnl.AddView(clv41.AsView, 0, 3%y, 85%x, 44%y)";
_pnl.AddView((android.view.View)(mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvv0().getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (85),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (44),mostCurrent.activityBA));
 //BA.debugLineNum = 133;BA.debugLine="For i = 0 To sbList.Size-1";
{
final int step110 = 1;
final int limit110 = (int) (mostCurrent._vvvvvvvvv0.getSize()-1);
for (_i = (int) (0); (step110 > 0 && _i <= limit110) || (step110 < 0 && _i >= limit110); _i = ((int)(0 + _i + step110))) {
 //BA.debugLineNum = 134;BA.debugLine="clv41.Add(CreateListItem41(sbList.Get(i), clv41.";
mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvv6(_vvvvvvvvvvvvvvvvvvvvvvv7(BA.ObjectToString(mostCurrent._vvvvvvvvv0.Get(_i)),mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvv0().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)),mostCurrent._vvvvvvvvv0.Get(_i));
 }
};
 //BA.debugLineNum = 136;BA.debugLine="Dim cd As CustomDialog2";
_cd = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog2();
 //BA.debugLineNum = 137;BA.debugLine="cd.AddView(pnl, 85%x, 50%y)";
_cd.AddView((android.view.View)(_pnl.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (85),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 138;BA.debugLine="Dim ret As Int = cd.Show(msg, \"تایید\", \"انصراف\",";
_ret = _cd.Show(_msg,"تایید","انصراف","هیچکدام",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 139;BA.debugLine="If ret = DialogResponse.POSITIVE Then";
if (_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 140;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 141;BA.debugLine="For i = 0 To clv41.GetSize-1";
{
final int step118 = 1;
final int limit118 = (int) (mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv4()-1);
for (_i = (int) (0); (step118 > 0 && _i <= limit118) || (step118 < 0 && _i >= limit118); _i = ((int)(0 + _i + step118))) {
 //BA.debugLineNum = 142;BA.debugLine="p = clv41.GetPanel(i)";
_p = mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv3(_i);
 //BA.debugLineNum = 143;BA.debugLine="If p.Tag = \"blue\" Then";
if ((_p.getTag()).equals((Object)("blue"))) { 
 //BA.debugLineNum = 144;BA.debugLine="Return clv41.GetValue(i)";
if (true) return BA.ObjectToString(mostCurrent._vvvvvv6._vvvvvvvvvvvvvvvvvvv5(_i));
 };
 }
};
 //BA.debugLineNum = 147;BA.debugLine="Return \"none\"";
if (true) return "none";
 }else if(_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
 //BA.debugLineNum = 149;BA.debugLine="Return \"none\"";
if (true) return "none";
 }else if(_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 151;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvv4(anywheresoftware.b4a.objects.PanelWrapper _parent,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _tt) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 39;BA.debugLine="Sub SetTypeface(parent As Panel, tt As Typeface)";
 //BA.debugLineNum = 40;BA.debugLine="For Each v As View In parent";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
final anywheresoftware.b4a.BA.IterableList group27 = _parent;
final int groupLen27 = group27.getSize();
for (int index27 = 0;index27 < groupLen27 ;index27++){
_v.setObject((android.view.View)(group27.Get(index27)));
 //BA.debugLineNum = 41;BA.debugLine="If v Is Panel Then";
if (_v.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 42;BA.debugLine="SetTypeface(v, tt)";
_vvvvvvvvvvvvvvvvvvvvv4((anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_v.getObject())),_tt);
 }else if(_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 44;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl.setObject((android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 45;BA.debugLine="lbl.Typeface = tt";
_lbl.setTypeface((android.graphics.Typeface)(_tt.getObject()));
 };
 }
;
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvvvvv0() throws Exception{
String _sb = "";
int _kk = 0;
 //BA.debugLineNum = 184;BA.debugLine="Sub startCalc";
 //BA.debugLineNum = 186;BA.debugLine="T(1)=ynBox(\"آیا پدر شخص فوت شده در قید حیات است؟\"";
_vvvvvv7[(int) (1)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا پدر شخص فوت شده در قید حیات است؟");
 //BA.debugLineNum = 187;BA.debugLine="T(2)=ynBox(\"آیا مادر شخص فوت شده در قید حیات است؟";
_vvvvvv7[(int) (2)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا مادر شخص فوت شده در قید حیات است؟");
 //BA.debugLineNum = 188;BA.debugLine="T(3)=ynBox(\"آیا فوت شده فرزند دارد؟\")";
_vvvvvv7[(int) (3)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده فرزند دارد؟");
 //BA.debugLineNum = 189;BA.debugLine="If T(3)= 1 Then";
if (_vvvvvv7[(int) (3)]==1) { 
 //BA.debugLineNum = 190;BA.debugLine="Np=inputBox(\"تعداد فرزندان پسر فوت شده را وارد ک";
_vvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد فرزندان پسر فوت شده را وارد کنید");
 //BA.debugLineNum = 191;BA.debugLine="Nd=inputBox(\"تعداد فرزندان دختر فوت شده را وارد";
_vvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد فرزندان دختر فوت شده را وارد کنید");
 }else {
 //BA.debugLineNum = 193;BA.debugLine="T(4)=ynBox(\"آیا فوت شده فرزند فرزند دارد؟\")";
_vvvvvv7[(int) (4)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده فرزند فرزند دارد؟");
 //BA.debugLineNum = 194;BA.debugLine="If T(4)= 1 Then";
if (_vvvvvv7[(int) (4)]==1) { 
 //BA.debugLineNum = 195;BA.debugLine="Mp=inputBox(\"تعداد فرزندان فرزند پسری فوت شده ر";
_vvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد فرزندان فرزند پسری فوت شده را وارد کنید");
 //BA.debugLineNum = 196;BA.debugLine="Md=inputBox(\"تعداد فرزندان فرزند دختری فوت شده";
_vvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد فرزندان فرزند دختری فوت شده را وارد کنید");
 };
 };
 //BA.debugLineNum = 199;BA.debugLine="T(5)=ynBox(\"آیا فوت شده مرد است؟\")";
_vvvvvv7[(int) (5)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده مرد است؟");
 //BA.debugLineNum = 200;BA.debugLine="If T(5)=1 Then";
if (_vvvvvv7[(int) (5)]==1) { 
 //BA.debugLineNum = 201;BA.debugLine="T(6)=ynBox(\"آیا فوت شده همسر عقدی دائم یا زنی که";
_vvvvvv7[(int) (6)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده همسر عقدی دائم یا زنی که طلاق رجعی شده و مرد در زمان عده وی فوت شده دارد؟");
 //BA.debugLineNum = 202;BA.debugLine="If T(6)=1 Then";
if (_vvvvvv7[(int) (6)]==1) { 
 //BA.debugLineNum = 203;BA.debugLine="Z=inputBox(\"تعداد همسران عقدی دائم فوت شده را و";
_vvvvvvvvvv5 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد همسران عقدی دائم فوت شده را وارد کنید");
 };
 }else {
 //BA.debugLineNum = 206;BA.debugLine="T(7)=ynBox(\"آیا فوت شده به عقد دائم مردی درآمده";
_vvvvvv7[(int) (7)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده به عقد دائم مردی درآمده است؟");
 };
 //BA.debugLineNum = 210;BA.debugLine="sbList.Clear";
mostCurrent._vvvvvvvvv0.Clear();
 //BA.debugLineNum = 211;BA.debugLine="If T(1)=1 Then";
if (_vvvvvv7[(int) (1)]==1) { 
 //BA.debugLineNum = 212;BA.debugLine="sbList.Add(\"پدر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("پدر"));
 };
 //BA.debugLineNum = 214;BA.debugLine="If T(2)=1 Then";
if (_vvvvvv7[(int) (2)]==1) { 
 //BA.debugLineNum = 215;BA.debugLine="sbList.Add(\"مادر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("مادر"));
 };
 //BA.debugLineNum = 217;BA.debugLine="If T(3)=1 Then";
if (_vvvvvv7[(int) (3)]==1) { 
 //BA.debugLineNum = 218;BA.debugLine="sbList.Add(\"فرزند پسر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("فرزند پسر"));
 //BA.debugLineNum = 219;BA.debugLine="sbList.Add(\"فرزند دختر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("فرزند دختر"));
 };
 //BA.debugLineNum = 221;BA.debugLine="If T(4)=1 Then";
if (_vvvvvv7[(int) (4)]==1) { 
 //BA.debugLineNum = 222;BA.debugLine="sbList.Add(\"فرزند فرزند پسر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("فرزند فرزند پسر"));
 //BA.debugLineNum = 223;BA.debugLine="sbList.Add(\"فرزند فرزند دختر\")";
mostCurrent._vvvvvvvvv0.Add((Object)("فرزند فرزند دختر"));
 };
 //BA.debugLineNum = 225;BA.debugLine="If T(5)=1  AND T(6)=1 Then";
if (_vvvvvv7[(int) (5)]==1 && _vvvvvv7[(int) (6)]==1) { 
 //BA.debugLineNum = 226;BA.debugLine="sbList.Add(\"زن عقدی دائم\")";
mostCurrent._vvvvvvvvv0.Add((Object)("زن عقدی دائم"));
 }else if(_vvvvvv7[(int) (5)]==0 && _vvvvvv7[(int) (7)]==1) { 
 //BA.debugLineNum = 228;BA.debugLine="sbList.Add(\"شوهر عقدی\")";
mostCurrent._vvvvvvvvv0.Add((Object)("شوهر عقدی"));
 };
 //BA.debugLineNum = 231;BA.debugLine="If sbList.Size = 0 Then";
if (mostCurrent._vvvvvvvvv0.getSize()==0) { 
 //BA.debugLineNum = 232;BA.debugLine="T(8)=0";
_vvvvvv7[(int) (8)] = (int) (0);
 }else {
 //BA.debugLineNum = 234;BA.debugLine="T(8)=1";
_vvvvvv7[(int) (8)] = (int) (1);
 //BA.debugLineNum = 235;BA.debugLine="Dim sb As String = selectBox(\"نسبت شما با فوت شد";
_sb = _vvvvvvvvvvvvvvvvvvvvvvvv1("نسبت شما با فوت شده کدامیک از موارد زیر است؟");
 //BA.debugLineNum = 236;BA.debugLine="If sb = \"پدر\" Then";
if ((_sb).equals("پدر")) { 
 //BA.debugLineNum = 237;BA.debugLine="W = 0";
_vvvvvvvvv1 = (int) (0);
 }else if((_sb).equals("مادر")) { 
 //BA.debugLineNum = 239;BA.debugLine="W = 1";
_vvvvvvvvv1 = (int) (1);
 }else if((_sb).equals("فرزند پسر")) { 
 //BA.debugLineNum = 241;BA.debugLine="W = 2";
_vvvvvvvvv1 = (int) (2);
 }else if((_sb).equals("فرزند دختر")) { 
 //BA.debugLineNum = 243;BA.debugLine="W = 3";
_vvvvvvvvv1 = (int) (3);
 }else if((_sb).equals("فرزند فرزند پسر")) { 
 //BA.debugLineNum = 245;BA.debugLine="W = 4";
_vvvvvvvvv1 = (int) (4);
 }else if((_sb).equals("فرزند فرزند دختر")) { 
 //BA.debugLineNum = 247;BA.debugLine="W = 5";
_vvvvvvvvv1 = (int) (5);
 }else if((_sb).equals("زن عقدی دائم")) { 
 //BA.debugLineNum = 249;BA.debugLine="W = 6";
_vvvvvvvvv1 = (int) (6);
 }else if((_sb).equals("شوهر عقدی")) { 
 //BA.debugLineNum = 251;BA.debugLine="W = 7";
_vvvvvvvvv1 = (int) (7);
 }else if((_sb).equals("none")) { 
 //BA.debugLineNum = 253;BA.debugLine="T(8)=0";
_vvvvvv7[(int) (8)] = (int) (0);
 };
 };
 //BA.debugLineNum = 257;BA.debugLine="If T(8) = 0 AND (T(1)=1 OR T(2)=1 OR T(3)=1 OR T(";
if (_vvvvvv7[(int) (8)]==0 && (_vvvvvv7[(int) (1)]==1 || _vvvvvv7[(int) (2)]==1 || _vvvvvv7[(int) (3)]==1 || _vvvvvv7[(int) (4)]==1)) { 
 //BA.debugLineNum = 258;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیرد";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 259;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 266;BA.debugLine="If T(6)=1 AND (T(3)=1 OR (T(3)=0 AND T(4)=1)) Th";
if (_vvvvvv7[(int) (6)]==1 && (_vvvvvv7[(int) (3)]==1 || (_vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1))) { 
 //BA.debugLineNum = 267;BA.debugLine="Q2=Q/(8*Z)";
_vvvvvvvvv4 = (float) (_vvvvvvvvv2/(double)(8*_vvvvvvvvvv5));
 //BA.debugLineNum = 268;BA.debugLine="Q1=Q-Q2";
_vvvvvvvvv3 = (float) (_vvvvvvvvv2-_vvvvvvvvv4);
 }else if(_vvvvvv7[(int) (6)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0) { 
 //BA.debugLineNum = 270;BA.debugLine="Q2=Q/(4*Z)";
_vvvvvvvvv4 = (float) (_vvvvvvvvv2/(double)(4*_vvvvvvvvvv5));
 //BA.debugLineNum = 271;BA.debugLine="Q1=Q-Q2";
_vvvvvvvvv3 = (float) (_vvvvvvvvv2-_vvvvvvvvv4);
 }else if(_vvvvvv7[(int) (7)]==1 && (_vvvvvv7[(int) (3)]==1 || (_vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==1))) { 
 //BA.debugLineNum = 273;BA.debugLine="Q2=Q/4";
_vvvvvvvvv4 = (float) (_vvvvvvvvv2/(double)4);
 //BA.debugLineNum = 274;BA.debugLine="Q1=Q-Q2";
_vvvvvvvvv3 = (float) (_vvvvvvvvv2-_vvvvvvvvv4);
 }else if(_vvvvvv7[(int) (7)]==1 && _vvvvvv7[(int) (3)]==0 && _vvvvvv7[(int) (4)]==0) { 
 //BA.debugLineNum = 276;BA.debugLine="Q2=Q/2";
_vvvvvvvvv4 = (float) (_vvvvvvvvv2/(double)2);
 //BA.debugLineNum = 277;BA.debugLine="Q1=Q-Q2";
_vvvvvvvvv3 = (float) (_vvvvvvvvv2-_vvvvvvvvv4);
 }else {
 //BA.debugLineNum = 279;BA.debugLine="Q1=100";
_vvvvvvvvv3 = (float) (100);
 //BA.debugLineNum = 280;BA.debugLine="Q2=0";
_vvvvvvvvv4 = (float) (0);
 };
 //BA.debugLineNum = 283;BA.debugLine="If W=6  OR W=7 Then";
if (_vvvvvvvvv1==6 || _vvvvvvvvv1==7) { 
 //BA.debugLineNum = 284;BA.debugLine="Label2.Text= NumberFormat(Q2,0,4) & \" درصد ارث ب";
mostCurrent._label2.setText((Object)(anywheresoftware.b4a.keywords.Common.NumberFormat(_vvvvvvvvv4,(int) (0),(int) (4))+" درصد ارث به شما تعلق میگیرد"));
 //BA.debugLineNum = 285;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 290;BA.debugLine="If T(8) = 1 Then";
if (_vvvvvv7[(int) (8)]==1) { 
 //BA.debugLineNum = 292;BA.debugLine="calc1";
_vvvvvvvvvvvvvvvvvvvvvvv1();
 //BA.debugLineNum = 293;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 296;BA.debugLine="T(10)=ynBox(\"آیا شما از وارثان درجه دوم (پدربزرگ،";
_vvvvvv7[(int) (10)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا شما از وارثان درجه دوم (پدربزرگ،مادربزرگ،خواهر،برادر،اولادخواهر،اولادبرادر) شخص فوت شده هستید؟");
 //BA.debugLineNum = 298;BA.debugLine="If T(8) = 0 AND T(10)=1 Then";
if (_vvvvvv7[(int) (8)]==0 && _vvvvvv7[(int) (10)]==1) { 
 //BA.debugLineNum = 301;BA.debugLine="sbList.Clear";
mostCurrent._vvvvvvvvv0.Clear();
 //BA.debugLineNum = 302;BA.debugLine="sbList.Add(\"پدربزرگ پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("پدربزرگ پدری"));
 //BA.debugLineNum = 303;BA.debugLine="sbList.Add(\"پدربزرگ مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("پدربزرگ مادری"));
 //BA.debugLineNum = 304;BA.debugLine="sbList.Add(\"مادربزرگ پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("مادربزرگ پدری"));
 //BA.debugLineNum = 305;BA.debugLine="sbList.Add(\"مادربزرگ مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("مادربزرگ مادری"));
 //BA.debugLineNum = 306;BA.debugLine="sbList.Add(\"برادر که پدر و مادر یکسان هستند\")";
mostCurrent._vvvvvvvvv0.Add((Object)("برادر که پدر و مادر یکسان هستند"));
 //BA.debugLineNum = 307;BA.debugLine="sbList.Add(\"خواهر که پدر و مادر یکسان هستند\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خواهر که پدر و مادر یکسان هستند"));
 //BA.debugLineNum = 308;BA.debugLine="sbList.Add(\"برادر که تنها پدر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("برادر که تنها پدر یکسان است"));
 //BA.debugLineNum = 309;BA.debugLine="sbList.Add(\"خواهر که تنها پدر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خواهر که تنها پدر یکسان است"));
 //BA.debugLineNum = 310;BA.debugLine="sbList.Add(\"برادر که تنها مادر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("برادر که تنها مادر یکسان است"));
 //BA.debugLineNum = 311;BA.debugLine="sbList.Add(\"خواهر که تنها مادر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خواهر که تنها مادر یکسان است"));
 //BA.debugLineNum = 312;BA.debugLine="sbList.Add(\"اولاد برادر که پدر و مادر یکسان است\"";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد برادر که پدر و مادر یکسان است"));
 //BA.debugLineNum = 313;BA.debugLine="sbList.Add(\"اولاد خواهر که پدر و مادر یکسان است\"";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خواهر که پدر و مادر یکسان است"));
 //BA.debugLineNum = 314;BA.debugLine="sbList.Add(\"اولاد برادر که تنها پدر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد برادر که تنها پدر یکسان است"));
 //BA.debugLineNum = 315;BA.debugLine="sbList.Add(\"اولاد خواهر که تنها پدر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خواهر که تنها پدر یکسان است"));
 //BA.debugLineNum = 316;BA.debugLine="sbList.Add(\"اولاد برادر که تنها مادر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد برادر که تنها مادر یکسان است"));
 //BA.debugLineNum = 317;BA.debugLine="sbList.Add(\"اولاد خواهر که تنها مادر یکسان است\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خواهر که تنها مادر یکسان است"));
 //BA.debugLineNum = 319;BA.debugLine="sb = selectBox(\"نسبت شما با فوت شده کدامیک از مو";
_sb = _vvvvvvvvvvvvvvvvvvvvvvvv1("نسبت شما با فوت شده کدامیک از موارد زیر است؟");
 //BA.debugLineNum = 320;BA.debugLine="If sb = \"پدربزرگ پدری\" Then";
if ((_sb).equals("پدربزرگ پدری")) { 
 //BA.debugLineNum = 321;BA.debugLine="W = 8";
_vvvvvvvvv1 = (int) (8);
 //BA.debugLineNum = 322;BA.debugLine="T(11)=1";
_vvvvvv7[(int) (11)] = (int) (1);
 }else if((_sb).equals("پدربزرگ مادری")) { 
 //BA.debugLineNum = 324;BA.debugLine="W = 81";
_vvvvvvvvv1 = (int) (81);
 //BA.debugLineNum = 325;BA.debugLine="T(19)=1";
_vvvvvv7[(int) (19)] = (int) (1);
 }else if((_sb).equals("مادربزرگ پدری")) { 
 //BA.debugLineNum = 327;BA.debugLine="W = 9";
_vvvvvvvvv1 = (int) (9);
 //BA.debugLineNum = 328;BA.debugLine="T(12)=1";
_vvvvvv7[(int) (12)] = (int) (1);
 }else if((_sb).equals("مادربزرگ مادری")) { 
 //BA.debugLineNum = 330;BA.debugLine="W = 91";
_vvvvvvvvv1 = (int) (91);
 //BA.debugLineNum = 331;BA.debugLine="T(20)=1";
_vvvvvv7[(int) (20)] = (int) (1);
 }else if((_sb).equals("برادر که پدر و مادر یکسان هستند")) { 
 //BA.debugLineNum = 333;BA.debugLine="W = 10";
_vvvvvvvvv1 = (int) (10);
 //BA.debugLineNum = 334;BA.debugLine="T(13)=1";
_vvvvvv7[(int) (13)] = (int) (1);
 }else if((_sb).equals("خواهر که پدر و مادر یکسان هستند")) { 
 //BA.debugLineNum = 336;BA.debugLine="W = 11";
_vvvvvvvvv1 = (int) (11);
 //BA.debugLineNum = 337;BA.debugLine="T(14)=1";
_vvvvvv7[(int) (14)] = (int) (1);
 }else if((_sb).equals("برادر که تنها پدر یکسان است")) { 
 //BA.debugLineNum = 339;BA.debugLine="W = 12";
_vvvvvvvvv1 = (int) (12);
 //BA.debugLineNum = 340;BA.debugLine="T(15)=1";
_vvvvvv7[(int) (15)] = (int) (1);
 }else if((_sb).equals("خواهر که تنها پدر یکسان است")) { 
 //BA.debugLineNum = 342;BA.debugLine="W = 13";
_vvvvvvvvv1 = (int) (13);
 //BA.debugLineNum = 343;BA.debugLine="T(16)=1";
_vvvvvv7[(int) (16)] = (int) (1);
 }else if((_sb).equals("برادر که تنها مادر یکسان است")) { 
 //BA.debugLineNum = 345;BA.debugLine="W = 14";
_vvvvvvvvv1 = (int) (14);
 //BA.debugLineNum = 346;BA.debugLine="T(17)=1";
_vvvvvv7[(int) (17)] = (int) (1);
 }else if((_sb).equals("خواهر که تنها مادر یکسان است")) { 
 //BA.debugLineNum = 348;BA.debugLine="W = 15";
_vvvvvvvvv1 = (int) (15);
 //BA.debugLineNum = 349;BA.debugLine="T(18)=1";
_vvvvvv7[(int) (18)] = (int) (1);
 }else if((_sb).equals("اولاد برادر که پدر و مادر یکسان است")) { 
 //BA.debugLineNum = 351;BA.debugLine="W = 16";
_vvvvvvvvv1 = (int) (16);
 //BA.debugLineNum = 352;BA.debugLine="T(21)=1";
_vvvvvv7[(int) (21)] = (int) (1);
 }else if((_sb).equals("اولاد خواهر که پدر و مادر یکسان است")) { 
 //BA.debugLineNum = 354;BA.debugLine="W = 17";
_vvvvvvvvv1 = (int) (17);
 //BA.debugLineNum = 355;BA.debugLine="T(22)=1";
_vvvvvv7[(int) (22)] = (int) (1);
 }else if((_sb).equals("اولاد برادر که تنها پدر یکسان است")) { 
 //BA.debugLineNum = 357;BA.debugLine="W = 18";
_vvvvvvvvv1 = (int) (18);
 //BA.debugLineNum = 358;BA.debugLine="T(23)=1";
_vvvvvv7[(int) (23)] = (int) (1);
 }else if((_sb).equals("اولاد خواهر که تنها پدر یکسان است")) { 
 //BA.debugLineNum = 360;BA.debugLine="W = 19";
_vvvvvvvvv1 = (int) (19);
 //BA.debugLineNum = 361;BA.debugLine="T(24)=1";
_vvvvvv7[(int) (24)] = (int) (1);
 }else if((_sb).equals("اولاد برادر که تنها مادر یکسان است")) { 
 //BA.debugLineNum = 363;BA.debugLine="W = 20";
_vvvvvvvvv1 = (int) (20);
 //BA.debugLineNum = 364;BA.debugLine="T(25)=1";
_vvvvvv7[(int) (25)] = (int) (1);
 }else if((_sb).equals("اولاد خواهر که تنها مادر یکسان است")) { 
 //BA.debugLineNum = 366;BA.debugLine="W = 21";
_vvvvvvvvv1 = (int) (21);
 //BA.debugLineNum = 367;BA.debugLine="T(26)=1";
_vvvvvv7[(int) (26)] = (int) (1);
 }else if((_sb).equals("none")) { 
 //BA.debugLineNum = 369;BA.debugLine="T(10)=0";
_vvvvvv7[(int) (10)] = (int) (0);
 };
 //BA.debugLineNum = 372;BA.debugLine="If T(10)=1 Then";
if (_vvvvvv7[(int) (10)]==1) { 
 //BA.debugLineNum = 373;BA.debugLine="If w<>8 Then T(11)=ynBox(\"آیا پدربزرگ پدری شخص";
if (_vvvvvvvvv1!=8) { 
_vvvvvv7[(int) (11)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا پدربزرگ پدری شخص فوت شده در قید حیات است؟");};
 //BA.debugLineNum = 374;BA.debugLine="If w<>9 Then T(12)=ynBox(\"آیا مادربزرگ پدری شخص";
if (_vvvvvvvvv1!=9) { 
_vvvvvv7[(int) (12)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا مادربزرگ پدری شخص فوت شده در قید حیات است؟");};
 //BA.debugLineNum = 375;BA.debugLine="If w<>10 Then T(13)=ynBox(\"آیا فوت شده برادری ب";
if (_vvvvvvvvv1!=10) { 
_vvvvvv7[(int) (13)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده برادری با پدر و مادر یکسان دارد؟");};
 //BA.debugLineNum = 376;BA.debugLine="If T(13)=1 Then";
if (_vvvvvv7[(int) (13)]==1) { 
 //BA.debugLineNum = 377;BA.debugLine="a1b = inputBox(\"تعداد برادران با پدر و مادر یک";
_vvvvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد برادران با پدر و مادر یکسان را وارد کنید");
 };
 //BA.debugLineNum = 379;BA.debugLine="If w<>11 Then T(14)=ynBox(\"آیا فوت شده خواهری ب";
if (_vvvvvvvvv1!=11) { 
_vvvvvv7[(int) (14)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خواهری با پدر و مادر یکسان دارد؟");};
 //BA.debugLineNum = 380;BA.debugLine="If T(14)=1 Then";
if (_vvvvvv7[(int) (14)]==1) { 
 //BA.debugLineNum = 381;BA.debugLine="a1k = inputBox(\"تعداد خواهران با پدر و مادر یک";
_vvvvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خواهران با پدر و مادر یکسان را وارد کنید");
 };
 //BA.debugLineNum = 383;BA.debugLine="If w<>12 Then T(15)=ynBox(\"آیا فوت شده برادری ک";
if (_vvvvvvvvv1!=12) { 
_vvvvvv7[(int) (15)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده برادری که تنها پدر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 384;BA.debugLine="If T(15)=1 Then";
if (_vvvvvv7[(int) (15)]==1) { 
 //BA.debugLineNum = 385;BA.debugLine="a2b = inputBox(\"تعداد برادران که تنها پدر یکسا";
_vvvvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد برادران که تنها پدر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 387;BA.debugLine="If w<>13 Then T(16)=ynBox(\"آیا فوت شده خواهری ک";
if (_vvvvvvvvv1!=13) { 
_vvvvvv7[(int) (16)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خواهری که تنها پدر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 388;BA.debugLine="If T(16)=1 Then";
if (_vvvvvv7[(int) (16)]==1) { 
 //BA.debugLineNum = 389;BA.debugLine="a2k = inputBox(\"تعداد خواهران که تنها پدر یکسا";
_vvvvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خواهران که تنها پدر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 391;BA.debugLine="If w<>14 Then T(17)=ynBox(\"آیا فوت شده برادری ک";
if (_vvvvvvvvv1!=14) { 
_vvvvvv7[(int) (17)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده برادری که تنها مادر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 392;BA.debugLine="If T(17)=1 Then";
if (_vvvvvv7[(int) (17)]==1) { 
 //BA.debugLineNum = 393;BA.debugLine="a3b = inputBox(\"تعداد برادران که تنها مادر یکس";
_vvvvvvvvvvv0 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد برادران که تنها مادر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 395;BA.debugLine="If w<>15 Then T(18)=ynBox(\"آیا فوت شده خواهری ک";
if (_vvvvvvvvv1!=15) { 
_vvvvvv7[(int) (18)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خواهری که تنها مادر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 396;BA.debugLine="If T(18)=1 Then";
if (_vvvvvv7[(int) (18)]==1) { 
 //BA.debugLineNum = 397;BA.debugLine="a3k = inputBox(\"تعداد خواهران که تنها مادر یکس";
_vvvvvvvvvvv7 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خواهران که تنها مادر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 399;BA.debugLine="If w<>81 Then T(19)=ynBox(\"آیا پدربزرگ مادری شخ";
if (_vvvvvvvvv1!=81) { 
_vvvvvv7[(int) (19)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا پدربزرگ مادری شخص فوت شده در قید حیات است؟");};
 //BA.debugLineNum = 400;BA.debugLine="If w<>91 Then T(20)=ynBox(\"آیا مادربزرگ مادری ش";
if (_vvvvvvvvv1!=91) { 
_vvvvvv7[(int) (20)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا مادربزرگ مادری شخص فوت شده در قید حیات است؟");};
 //BA.debugLineNum = 401;BA.debugLine="If w<>16 AND T(13)=0 Then T(21)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=16 && _vvvvvv7[(int) (13)]==0) { 
_vvvvvv7[(int) (21)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد برادر  با پدر و مادر یکسان دارد؟");};
 //BA.debugLineNum = 402;BA.debugLine="If T(21)=1 Then";
if (_vvvvvv7[(int) (21)]==1) { 
 //BA.debugLineNum = 403;BA.debugLine="b1b = inputBox(\"تعداد اولاد برادر  با پدر و ما";
_vvvvvvvvvvv6 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد برادر  با پدر و مادر یکسان را وارد کنید");
 };
 //BA.debugLineNum = 405;BA.debugLine="If w<>17 AND T(14)=0 Then T(22)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=17 && _vvvvvv7[(int) (14)]==0) { 
_vvvvvv7[(int) (22)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خواهر با پدر و مادر یکسان دارد؟");};
 //BA.debugLineNum = 406;BA.debugLine="If T(22)=1 Then";
if (_vvvvvv7[(int) (22)]==1) { 
 //BA.debugLineNum = 407;BA.debugLine="b1k = inputBox(\"تعداد اولاد خواهر  با پدر و ما";
_vvvvvvvvvvv5 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خواهر  با پدر و مادر یکسان را وارد کنید");
 };
 //BA.debugLineNum = 409;BA.debugLine="If w<>18 AND T(15)=0 Then T(23)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=18 && _vvvvvv7[(int) (15)]==0) { 
_vvvvvv7[(int) (23)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد برادر  که تنها پدر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 410;BA.debugLine="If T(23)=1 Then";
if (_vvvvvv7[(int) (23)]==1) { 
 //BA.debugLineNum = 411;BA.debugLine="b2b = inputBox(\"تعداد اولاد برادر  که تنها پدر";
_vvvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد برادر  که تنها پدر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 413;BA.debugLine="If w<>19 AND T(16)=0 Then T(24)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=19 && _vvvvvv7[(int) (16)]==0) { 
_vvvvvv7[(int) (24)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خواهر  که تنها پدر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 414;BA.debugLine="If T(24)=1 Then";
if (_vvvvvv7[(int) (24)]==1) { 
 //BA.debugLineNum = 415;BA.debugLine="b2k = inputBox(\"تعداد اولاد خواهر  که تنها پدر";
_vvvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خواهر  که تنها پدر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 417;BA.debugLine="If w<>20 AND T(17)=0 Then T(25)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=20 && _vvvvvv7[(int) (17)]==0) { 
_vvvvvv7[(int) (25)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد برادر که تنها مادر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 418;BA.debugLine="If T(25)=1 Then";
if (_vvvvvv7[(int) (25)]==1) { 
 //BA.debugLineNum = 419;BA.debugLine="b3b = inputBox(\"تعداد اولاد برادر  که تنها ماد";
_vvvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد برادر  که تنها مادر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 421;BA.debugLine="If w<>21 AND T(18)=0 Then T(26)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=21 && _vvvvvv7[(int) (18)]==0) { 
_vvvvvv7[(int) (26)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خواهر  که تنها مادر یکسان دارند، دارد؟");};
 //BA.debugLineNum = 422;BA.debugLine="If T(26)=1 Then";
if (_vvvvvv7[(int) (26)]==1) { 
 //BA.debugLineNum = 423;BA.debugLine="b3k = inputBox(\"تعداد اولاد خواهر  که تنها ماد";
_vvvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خواهر  که تنها مادر یکسان دارند را وارد کنید");
 };
 //BA.debugLineNum = 425;BA.debugLine="L3 = 2*a1b + a1k";
_vvvvvvvvvv6 = (int) (2*_vvvvvvvvvvvv4+_vvvvvvvvvvvv3);
 //BA.debugLineNum = 426;BA.debugLine="L4 = 2*a2b + a2k";
_vvvvvvvvvv7 = (int) (2*_vvvvvvvvvvvv2+_vvvvvvvvvvvv1);
 //BA.debugLineNum = 427;BA.debugLine="L5 = 2*a3b + a3k";
_vvvvvvvvvv0 = (int) (2*_vvvvvvvvvvv0+_vvvvvvvvvvv7);
 //BA.debugLineNum = 428;BA.debugLine="calc2";
_vvvvvvvvvvvvvvvvvvvvvvv3();
 //BA.debugLineNum = 429;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 433;BA.debugLine="If T(8) = 0 AND T(10)=0 Then";
if (_vvvvvv7[(int) (8)]==0 && _vvvvvv7[(int) (10)]==0) { 
 //BA.debugLineNum = 435;BA.debugLine="T(27) = ynBox(\"آیا شما از وراث طبقه سوم (عمو،عمه";
_vvvvvv7[(int) (27)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا شما از وراث طبقه سوم (عمو،عمه،دایی،خاله یا اولاد آنها) هستید؟");
 //BA.debugLineNum = 437;BA.debugLine="Dim KK As Int =ynBox(\"آیا یکی از وارثان درجه دوم";
_kk = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا یکی از وارثان درجه دوم شخص فوت شده در قید حیات است؟ (پدربزرگ،مادربزرگ،خواهر،برادر،اولادخواهر،اولادبرادر)");
 //BA.debugLineNum = 438;BA.debugLine="If T(27)=1 AND (isOne(1,4) OR isOne(11,26) OR KK";
if (_vvvvvv7[(int) (27)]==1 && (_vvvvvvvvvvvvvvvvvvvvvvv5((int) (1),(int) (4)) || _vvvvvvvvvvvvvvvvvvvvvvv5((int) (11),(int) (26)) || _kk==1)) { 
 //BA.debugLineNum = 439;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیر";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 440;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 442;BA.debugLine="If isZero(1,4) AND isZero(11,26) Then";
if (_vvvvvvvvvvvvvvvvvvvvvvv4((int) (1),(int) (4)) && _vvvvvvvvvvvvvvvvvvvvvvv4((int) (11),(int) (26))) { 
 //BA.debugLineNum = 443;BA.debugLine="POW = 1";
_vvvvvvv7 = (int) (1);
 };
 //BA.debugLineNum = 445;BA.debugLine="If T(27)=1 AND POW = 1 Then";
if (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) { 
 //BA.debugLineNum = 446;BA.debugLine="sbList.Clear";
mostCurrent._vvvvvvvvv0.Clear();
 //BA.debugLineNum = 447;BA.debugLine="sbList.Add(\"عموی پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عموی پدری و مادری"));
 //BA.debugLineNum = 448;BA.debugLine="sbList.Add(\"عمه پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عمه پدری و مادری"));
 //BA.debugLineNum = 449;BA.debugLine="sbList.Add(\"عموی پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عموی پدری"));
 //BA.debugLineNum = 450;BA.debugLine="sbList.Add(\"عمه پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عمه پدری"));
 //BA.debugLineNum = 451;BA.debugLine="sbList.Add(\"عموی مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عموی مادری"));
 //BA.debugLineNum = 452;BA.debugLine="sbList.Add(\"عمه مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("عمه مادری"));
 //BA.debugLineNum = 453;BA.debugLine="sbList.Add(\"خاله پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خاله پدری و مادری"));
 //BA.debugLineNum = 454;BA.debugLine="sbList.Add(\"دایی پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("دایی پدری و مادری"));
 //BA.debugLineNum = 455;BA.debugLine="sbList.Add(\"خاله پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خاله پدری"));
 //BA.debugLineNum = 456;BA.debugLine="sbList.Add(\"دایی پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("دایی پدری"));
 //BA.debugLineNum = 457;BA.debugLine="sbList.Add(\"خاله مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("خاله مادری"));
 //BA.debugLineNum = 458;BA.debugLine="sbList.Add(\"دایی مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("دایی مادری"));
 //BA.debugLineNum = 459;BA.debugLine="sbList.Add(\"اولاد عموی پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عموی پدری و مادری"));
 //BA.debugLineNum = 460;BA.debugLine="sbList.Add(\"اولاد عمه پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عمه پدری و مادری"));
 //BA.debugLineNum = 461;BA.debugLine="sbList.Add(\"اولاد عموی پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عموی پدری"));
 //BA.debugLineNum = 462;BA.debugLine="sbList.Add(\"اولاد عمه پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عمه پدری"));
 //BA.debugLineNum = 463;BA.debugLine="sbList.Add(\"اولاد عموی مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عموی مادری"));
 //BA.debugLineNum = 464;BA.debugLine="sbList.Add(\"اولاد عمه مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد عمه مادری"));
 //BA.debugLineNum = 465;BA.debugLine="sbList.Add(\"اولاد خاله پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خاله پدری و مادری"));
 //BA.debugLineNum = 466;BA.debugLine="sbList.Add(\"اولاد دایی پدری و مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد دایی پدری و مادری"));
 //BA.debugLineNum = 467;BA.debugLine="sbList.Add(\"اولاد خاله پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خاله پدری"));
 //BA.debugLineNum = 468;BA.debugLine="sbList.Add(\"اولاد دایی پدری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد دایی پدری"));
 //BA.debugLineNum = 469;BA.debugLine="sbList.Add(\"اولاد خاله مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد خاله مادری"));
 //BA.debugLineNum = 470;BA.debugLine="sbList.Add(\"اولاد دایی مادری\")";
mostCurrent._vvvvvvvvv0.Add((Object)("اولاد دایی مادری"));
 //BA.debugLineNum = 471;BA.debugLine="sb = selectBox(\"نسبت شما با فوت شده کدامیک از م";
_sb = _vvvvvvvvvvvvvvvvvvvvvvvv1("نسبت شما با فوت شده کدامیک از موارد زیر است؟");
 //BA.debugLineNum = 472;BA.debugLine="If sb = \"عموی پدری و مادری\" Then";
if ((_sb).equals("عموی پدری و مادری")) { 
 //BA.debugLineNum = 473;BA.debugLine="W = 22";
_vvvvvvvvv1 = (int) (22);
 //BA.debugLineNum = 474;BA.debugLine="T(28)=1";
_vvvvvv7[(int) (28)] = (int) (1);
 }else if((_sb).equals("عمه پدری و مادری")) { 
 //BA.debugLineNum = 476;BA.debugLine="W = 23";
_vvvvvvvvv1 = (int) (23);
 //BA.debugLineNum = 477;BA.debugLine="T(29)=1";
_vvvvvv7[(int) (29)] = (int) (1);
 }else if((_sb).equals("عموی پدری")) { 
 //BA.debugLineNum = 479;BA.debugLine="W = 24";
_vvvvvvvvv1 = (int) (24);
 //BA.debugLineNum = 480;BA.debugLine="T(30)=1";
_vvvvvv7[(int) (30)] = (int) (1);
 }else if((_sb).equals("عمه پدری")) { 
 //BA.debugLineNum = 482;BA.debugLine="W = 25";
_vvvvvvvvv1 = (int) (25);
 //BA.debugLineNum = 483;BA.debugLine="T(31)=1";
_vvvvvv7[(int) (31)] = (int) (1);
 }else if((_sb).equals("عموی مادری")) { 
 //BA.debugLineNum = 485;BA.debugLine="W = 26";
_vvvvvvvvv1 = (int) (26);
 //BA.debugLineNum = 486;BA.debugLine="T(32)=1";
_vvvvvv7[(int) (32)] = (int) (1);
 }else if((_sb).equals("عمه مادری")) { 
 //BA.debugLineNum = 488;BA.debugLine="W = 27";
_vvvvvvvvv1 = (int) (27);
 //BA.debugLineNum = 489;BA.debugLine="T(33)=1";
_vvvvvv7[(int) (33)] = (int) (1);
 }else if((_sb).equals("خاله پدری و مادری")) { 
 //BA.debugLineNum = 491;BA.debugLine="W = 28";
_vvvvvvvvv1 = (int) (28);
 //BA.debugLineNum = 492;BA.debugLine="T(34)=1";
_vvvvvv7[(int) (34)] = (int) (1);
 }else if((_sb).equals("دایی پدری و مادری")) { 
 //BA.debugLineNum = 494;BA.debugLine="W = 29";
_vvvvvvvvv1 = (int) (29);
 //BA.debugLineNum = 495;BA.debugLine="T(35)=1";
_vvvvvv7[(int) (35)] = (int) (1);
 }else if((_sb).equals("خاله پدری")) { 
 //BA.debugLineNum = 497;BA.debugLine="W = 30";
_vvvvvvvvv1 = (int) (30);
 //BA.debugLineNum = 498;BA.debugLine="T(36)=1";
_vvvvvv7[(int) (36)] = (int) (1);
 }else if((_sb).equals("دایی پدری")) { 
 //BA.debugLineNum = 500;BA.debugLine="W = 31";
_vvvvvvvvv1 = (int) (31);
 //BA.debugLineNum = 501;BA.debugLine="T(37)=1";
_vvvvvv7[(int) (37)] = (int) (1);
 }else if((_sb).equals("خاله مادری")) { 
 //BA.debugLineNum = 503;BA.debugLine="W = 32";
_vvvvvvvvv1 = (int) (32);
 //BA.debugLineNum = 504;BA.debugLine="T(38)=1";
_vvvvvv7[(int) (38)] = (int) (1);
 }else if((_sb).equals("دایی مادری")) { 
 //BA.debugLineNum = 506;BA.debugLine="W = 33";
_vvvvvvvvv1 = (int) (33);
 //BA.debugLineNum = 507;BA.debugLine="T(39)=1";
_vvvvvv7[(int) (39)] = (int) (1);
 }else if((_sb).equals("اولاد عموی پدری و مادری")) { 
 //BA.debugLineNum = 509;BA.debugLine="W = 34";
_vvvvvvvvv1 = (int) (34);
 //BA.debugLineNum = 510;BA.debugLine="T(40)=1";
_vvvvvv7[(int) (40)] = (int) (1);
 }else if((_sb).equals("اولاد عمه پدری و مادری")) { 
 //BA.debugLineNum = 512;BA.debugLine="W = 35";
_vvvvvvvvv1 = (int) (35);
 //BA.debugLineNum = 513;BA.debugLine="T(41)=1";
_vvvvvv7[(int) (41)] = (int) (1);
 }else if((_sb).equals("اولاد عموی پدری")) { 
 //BA.debugLineNum = 515;BA.debugLine="W = 36";
_vvvvvvvvv1 = (int) (36);
 //BA.debugLineNum = 516;BA.debugLine="T(42)=1";
_vvvvvv7[(int) (42)] = (int) (1);
 }else if((_sb).equals("اولاد عمه پدری")) { 
 //BA.debugLineNum = 518;BA.debugLine="W = 37";
_vvvvvvvvv1 = (int) (37);
 //BA.debugLineNum = 519;BA.debugLine="T(43)=1";
_vvvvvv7[(int) (43)] = (int) (1);
 }else if((_sb).equals("اولاد عموی مادری")) { 
 //BA.debugLineNum = 521;BA.debugLine="W = 38";
_vvvvvvvvv1 = (int) (38);
 //BA.debugLineNum = 522;BA.debugLine="T(44)=1";
_vvvvvv7[(int) (44)] = (int) (1);
 }else if((_sb).equals("اولاد عمه مادری")) { 
 //BA.debugLineNum = 524;BA.debugLine="W = 39";
_vvvvvvvvv1 = (int) (39);
 //BA.debugLineNum = 525;BA.debugLine="T(45)=1";
_vvvvvv7[(int) (45)] = (int) (1);
 }else if((_sb).equals("اولاد خاله پدری و مادری")) { 
 //BA.debugLineNum = 527;BA.debugLine="W = 40";
_vvvvvvvvv1 = (int) (40);
 //BA.debugLineNum = 528;BA.debugLine="T(46)=1";
_vvvvvv7[(int) (46)] = (int) (1);
 }else if((_sb).equals("اولاد دایی پدری و مادری")) { 
 //BA.debugLineNum = 530;BA.debugLine="W = 41";
_vvvvvvvvv1 = (int) (41);
 //BA.debugLineNum = 531;BA.debugLine="T(47)=1";
_vvvvvv7[(int) (47)] = (int) (1);
 }else if((_sb).equals("اولاد خاله پدری")) { 
 //BA.debugLineNum = 533;BA.debugLine="W = 42";
_vvvvvvvvv1 = (int) (42);
 //BA.debugLineNum = 534;BA.debugLine="T(48)=1";
_vvvvvv7[(int) (48)] = (int) (1);
 }else if((_sb).equals("اولاد دایی پدری")) { 
 //BA.debugLineNum = 536;BA.debugLine="W = 43";
_vvvvvvvvv1 = (int) (43);
 //BA.debugLineNum = 537;BA.debugLine="T(49)=1";
_vvvvvv7[(int) (49)] = (int) (1);
 }else if((_sb).equals("اولاد خاله مادری")) { 
 //BA.debugLineNum = 539;BA.debugLine="W = 44";
_vvvvvvvvv1 = (int) (44);
 //BA.debugLineNum = 540;BA.debugLine="T(50)=1";
_vvvvvv7[(int) (50)] = (int) (1);
 }else if((_sb).equals("اولاد دایی مادری")) { 
 //BA.debugLineNum = 542;BA.debugLine="W = 45";
_vvvvvvvvv1 = (int) (45);
 //BA.debugLineNum = 543;BA.debugLine="T(51)=1";
_vvvvvv7[(int) (51)] = (int) (1);
 }else if((_sb).equals("none")) { 
 //BA.debugLineNum = 545;BA.debugLine="T(27)=0";
_vvvvvv7[(int) (27)] = (int) (0);
 };
 };
 //BA.debugLineNum = 548;BA.debugLine="If T(27)=1 AND POW=1 Then";
if (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) { 
 //BA.debugLineNum = 549;BA.debugLine="If w<>22 Then T(28)=ynBox(\"آیا فوت شده عموی پدر";
if (_vvvvvvvvv1!=22) { 
_vvvvvv7[(int) (28)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عموی پدری و مادری دارد؟");};
 //BA.debugLineNum = 550;BA.debugLine="If T(28)=1 Then";
if (_vvvvvv7[(int) (28)]==1) { 
 //BA.debugLineNum = 551;BA.debugLine="c22=inputBox(\"تعداد عموهای پدری و مادری را وار";
_vvvvvvvvvvvv5 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عموهای پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 553;BA.debugLine="If w<>23 Then T(29)=ynBox(\"آیا فوت شده عمه پدری";
if (_vvvvvvvvv1!=23) { 
_vvvvvv7[(int) (29)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عمه پدری و مادری دارد؟");};
 //BA.debugLineNum = 554;BA.debugLine="If T(29)=1 Then";
if (_vvvvvv7[(int) (29)]==1) { 
 //BA.debugLineNum = 555;BA.debugLine="c23=inputBox(\"تعداد عمه های پدری و مادری را وا";
_vvvvvvvvvvvv6 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عمه های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 557;BA.debugLine="If w<>24 Then T(30)=ynBox(\"آیا فوت شده عموی پدر";
if (_vvvvvvvvv1!=24) { 
_vvvvvv7[(int) (30)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عموی پدری  دارد؟");};
 //BA.debugLineNum = 558;BA.debugLine="If T(30)=1 Then";
if (_vvvvvv7[(int) (30)]==1) { 
 //BA.debugLineNum = 559;BA.debugLine="c24=inputBox(\"تعداد عموهای پدری  را وارد کنید\"";
_vvvvvvvvvvvv7 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عموهای پدری  را وارد کنید");
 };
 //BA.debugLineNum = 561;BA.debugLine="If w<>25 Then T(31)=ynBox(\"آیا فوت شده عمه پدری";
if (_vvvvvvvvv1!=25) { 
_vvvvvv7[(int) (31)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عمه پدری  دارد؟");};
 //BA.debugLineNum = 562;BA.debugLine="If T(31)=1 Then";
if (_vvvvvv7[(int) (31)]==1) { 
 //BA.debugLineNum = 563;BA.debugLine="c25=inputBox(\"تعداد عمه های پدری  را وارد کنید";
_vvvvvvvvvvvv0 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عمه های پدری  را وارد کنید");
 };
 //BA.debugLineNum = 565;BA.debugLine="If w<>26 Then T(32)=ynBox(\"آیا فوت شده عموی ماد";
if (_vvvvvvvvv1!=26) { 
_vvvvvv7[(int) (32)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عموی مادری  دارد؟");};
 //BA.debugLineNum = 566;BA.debugLine="If T(32)=1 Then";
if (_vvvvvv7[(int) (32)]==1) { 
 //BA.debugLineNum = 567;BA.debugLine="c26=inputBox(\"تعداد عموهای مادری  را وارد کنید";
_vvvvvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عموهای مادری  را وارد کنید");
 };
 //BA.debugLineNum = 569;BA.debugLine="If w<>27 Then T(33)=ynBox(\"آیا فوت شده عمه مادر";
if (_vvvvvvvvv1!=27) { 
_vvvvvv7[(int) (33)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده عمه مادری  دارد؟");};
 //BA.debugLineNum = 570;BA.debugLine="If T(33)=1 Then";
if (_vvvvvv7[(int) (33)]==1) { 
 //BA.debugLineNum = 571;BA.debugLine="c27=inputBox(\"تعداد عمه های مادری  را وارد کنی";
_vvvvvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد عمه های مادری  را وارد کنید");
 };
 //BA.debugLineNum = 573;BA.debugLine="If w<>28 Then T(34)=ynBox(\"آیا فوت شده خاله پدر";
if (_vvvvvvvvv1!=28) { 
_vvvvvv7[(int) (34)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خاله پدر و مادری دارد؟");};
 //BA.debugLineNum = 574;BA.debugLine="If T(34)=1 Then";
if (_vvvvvv7[(int) (34)]==1) { 
 //BA.debugLineNum = 575;BA.debugLine="c28=inputBox(\"تعداد خاله های پدری و مادری را و";
_vvvvvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خاله های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 577;BA.debugLine="If w<>29 Then T(35)=ynBox(\"آیا فوت شده دایی پدر";
if (_vvvvvvvvv1!=29) { 
_vvvvvv7[(int) (35)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده دایی پدر و مادری دارد؟");};
 //BA.debugLineNum = 578;BA.debugLine="If T(35)=1 Then";
if (_vvvvvv7[(int) (35)]==1) { 
 //BA.debugLineNum = 579;BA.debugLine="c29=inputBox(\"تعداد دایی های پدری و مادری را و";
_vvvvvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد دایی های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 581;BA.debugLine="If w<>30 Then T(36)=ynBox(\"آیا فوت شده خاله پدر";
if (_vvvvvvvvv1!=30) { 
_vvvvvv7[(int) (36)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خاله پدری دارد؟");};
 //BA.debugLineNum = 582;BA.debugLine="If T(36)=1 Then";
if (_vvvvvv7[(int) (36)]==1) { 
 //BA.debugLineNum = 583;BA.debugLine="c30=inputBox(\"تعداد خاله های پدری را وارد کنید";
_vvvvvvvvvvvvv5 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خاله های پدری را وارد کنید");
 };
 //BA.debugLineNum = 585;BA.debugLine="If w<>31 Then T(37)=ynBox(\"آیا فوت شده دایی پدر";
if (_vvvvvvvvv1!=31) { 
_vvvvvv7[(int) (37)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده دایی پدری دارد؟");};
 //BA.debugLineNum = 586;BA.debugLine="If T(37)=1 Then";
if (_vvvvvv7[(int) (37)]==1) { 
 //BA.debugLineNum = 587;BA.debugLine="c31=inputBox(\"تعداد دایی های پدری را وارد کنید";
_vvvvvvvvvvvvv6 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد دایی های پدری را وارد کنید");
 };
 //BA.debugLineNum = 589;BA.debugLine="If w<>32 Then T(38)=ynBox(\"آیا فوت شده خاله ماد";
if (_vvvvvvvvv1!=32) { 
_vvvvvv7[(int) (38)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده خاله مادری دارد؟");};
 //BA.debugLineNum = 590;BA.debugLine="If T(38)=1 Then";
if (_vvvvvv7[(int) (38)]==1) { 
 //BA.debugLineNum = 591;BA.debugLine="c32=inputBox(\"تعداد خاله های مادری را وارد کنی";
_vvvvvvvvvvvvv7 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد خاله های مادری را وارد کنید");
 };
 //BA.debugLineNum = 593;BA.debugLine="If w<>33 Then T(39)=ynBox(\"آیا فوت شده دایی ماد";
if (_vvvvvvvvv1!=33) { 
_vvvvvv7[(int) (39)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده دایی مادری دارد؟");};
 //BA.debugLineNum = 594;BA.debugLine="If T(39)=1 Then";
if (_vvvvvv7[(int) (39)]==1) { 
 //BA.debugLineNum = 595;BA.debugLine="c33=inputBox(\"تعداد دایی های مادری را وارد کنی";
_vvvvvvvvvvvvv0 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد دایی های مادری را وارد کنید");
 };
 //BA.debugLineNum = 598;BA.debugLine="If (w=34 AND T(28)=1) OR (w=35 AND T(29)=1) OR";
if ((_vvvvvvvvv1==34 && _vvvvvv7[(int) (28)]==1) || (_vvvvvvvvv1==35 && _vvvvvv7[(int) (29)]==1) || (_vvvvvvvvv1==36 && _vvvvvv7[(int) (30)]==1) || (_vvvvvvvvv1==37 && _vvvvvv7[(int) (31)]==1) || (_vvvvvvvvv1==38 && _vvvvvv7[(int) (32)]==1) || (_vvvvvvvvv1==39 && _vvvvvv7[(int) (33)]==1) || (_vvvvvvvvv1==40 && _vvvvvv7[(int) (34)]==1) || (_vvvvvvvvv1==41 && _vvvvvv7[(int) (35)]==1) || (_vvvvvvvvv1==42 && _vvvvvv7[(int) (36)]==1) || (_vvvvvvvvv1==43 && _vvvvvv7[(int) (37)]==1) || (_vvvvvvvvv1==44 && _vvvvvv7[(int) (38)]==1) || (_vvvvvvvvv1==45 && _vvvvvv7[(int) (39)]==1)) { 
 //BA.debugLineNum = 602;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگی";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 603;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 606;BA.debugLine="If T(27)=1 AND POW=1 Then";
if (_vvvvvv7[(int) (27)]==1 && _vvvvvvv7==1) { 
 //BA.debugLineNum = 607;BA.debugLine="If w<>34 AND T(28)=0 Then T(40)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=34 && _vvvvvv7[(int) (28)]==0) { 
_vvvvvv7[(int) (40)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد عموی پدری و مادری دارد؟");};
 //BA.debugLineNum = 608;BA.debugLine="If T(40)=1 Then";
if (_vvvvvv7[(int) (40)]==1) { 
 //BA.debugLineNum = 609;BA.debugLine="c34=inputBox(\"تعداد اولاد عموهای پدری و مادری";
_vvvvvvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عموهای پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 611;BA.debugLine="If w<>35 AND T(29)=0 Then T(41)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=35 && _vvvvvv7[(int) (29)]==0) { 
_vvvvvv7[(int) (41)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد عمه پدری و مادری دارد؟");};
 //BA.debugLineNum = 612;BA.debugLine="If T(41)=1 Then";
if (_vvvvvv7[(int) (41)]==1) { 
 //BA.debugLineNum = 613;BA.debugLine="c35=inputBox(\"تعداد اولاد عمه های پدری و مادری";
_vvvvvvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عمه های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 615;BA.debugLine="If w<>36 AND T(30)=0 Then T(42)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=36 && _vvvvvv7[(int) (30)]==0) { 
_vvvvvv7[(int) (42)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد عموی پدری  دارد؟");};
 //BA.debugLineNum = 616;BA.debugLine="If T(42)=1 Then";
if (_vvvvvv7[(int) (42)]==1) { 
 //BA.debugLineNum = 617;BA.debugLine="c36=inputBox(\"تعداد اولاد عموهای پدری  را وارد";
_vvvvvvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عموهای پدری  را وارد کنید");
 };
 //BA.debugLineNum = 619;BA.debugLine="If w<>37 AND T(31)=0 Then T(43)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=37 && _vvvvvv7[(int) (31)]==0) { 
_vvvvvv7[(int) (43)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت اولاد شده عمه پدری  دارد؟");};
 //BA.debugLineNum = 620;BA.debugLine="If T(43)=1 Then";
if (_vvvvvv7[(int) (43)]==1) { 
 //BA.debugLineNum = 621;BA.debugLine="c37=inputBox(\"تعداد اولاد عمه های پدری  را وار";
_vvvvvvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عمه های پدری  را وارد کنید");
 };
 //BA.debugLineNum = 623;BA.debugLine="If w<>38 AND T(32)=0 Then T(44)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=38 && _vvvvvv7[(int) (32)]==0) { 
_vvvvvv7[(int) (44)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد عموی مادری  دارد؟");};
 //BA.debugLineNum = 624;BA.debugLine="If T(44)=1 Then";
if (_vvvvvv7[(int) (44)]==1) { 
 //BA.debugLineNum = 625;BA.debugLine="c38=inputBox(\"تعداد اولاد عموهای مادری  را وار";
_vvvvvvvvvvvvvv5 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عموهای مادری  را وارد کنید");
 };
 //BA.debugLineNum = 627;BA.debugLine="If w<>39 AND T(33)=0 Then T(45)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=39 && _vvvvvv7[(int) (33)]==0) { 
_vvvvvv7[(int) (45)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد عمه مادری  دارد؟");};
 //BA.debugLineNum = 628;BA.debugLine="If T(45)=1 Then";
if (_vvvvvv7[(int) (45)]==1) { 
 //BA.debugLineNum = 629;BA.debugLine="c39=inputBox(\"تعداد اولاد عمه های مادری  را وا";
_vvvvvvvvvvvvvv6 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد عمه های مادری  را وارد کنید");
 };
 //BA.debugLineNum = 631;BA.debugLine="If w<>40 AND T(34)=0 Then T(46)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=40 && _vvvvvv7[(int) (34)]==0) { 
_vvvvvv7[(int) (46)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خاله پدر و مادری دارد؟");};
 //BA.debugLineNum = 632;BA.debugLine="If T(46)=1 Then";
if (_vvvvvv7[(int) (46)]==1) { 
 //BA.debugLineNum = 633;BA.debugLine="c40=inputBox(\"تعداد اولاد خاله های پدری و مادر";
_vvvvvvvvvvvvvv7 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خاله های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 635;BA.debugLine="If w<>41 AND T(35)=0 Then T(47)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=41 && _vvvvvv7[(int) (35)]==0) { 
_vvvvvv7[(int) (47)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد دایی پدر و مادری دارد؟");};
 //BA.debugLineNum = 636;BA.debugLine="If T(47)=1 Then";
if (_vvvvvv7[(int) (47)]==1) { 
 //BA.debugLineNum = 637;BA.debugLine="c41=inputBox(\"تعداد اولاد دایی های پدری و مادر";
_vvvvvvvvvvvvvv0 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد دایی های پدری و مادری را وارد کنید");
 };
 //BA.debugLineNum = 639;BA.debugLine="If w<>42 AND T(36)=0 Then T(48)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=42 && _vvvvvv7[(int) (36)]==0) { 
_vvvvvv7[(int) (48)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خاله پدری دارد؟");};
 //BA.debugLineNum = 640;BA.debugLine="If T(48)=1 Then";
if (_vvvvvv7[(int) (48)]==1) { 
 //BA.debugLineNum = 641;BA.debugLine="c42=inputBox(\"تعداد اولاد خاله های پدری را وار";
_vvvvvvvvvvvvvvv1 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خاله های پدری را وارد کنید");
 };
 //BA.debugLineNum = 643;BA.debugLine="If w<>43 AND T(37)=0 Then T(49)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=43 && _vvvvvv7[(int) (37)]==0) { 
_vvvvvv7[(int) (49)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد دایی پدری دارد؟");};
 //BA.debugLineNum = 644;BA.debugLine="If T(49)=1 Then";
if (_vvvvvv7[(int) (49)]==1) { 
 //BA.debugLineNum = 645;BA.debugLine="c43=inputBox(\"تعداد اولاد دایی های پدری را وار";
_vvvvvvvvvvvvvvv2 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد دایی های پدری را وارد کنید");
 };
 //BA.debugLineNum = 647;BA.debugLine="If w<>44 AND T(38)=0 Then T(50)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=44 && _vvvvvv7[(int) (38)]==0) { 
_vvvvvv7[(int) (50)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد خاله مادری دارد؟");};
 //BA.debugLineNum = 648;BA.debugLine="If T(50)=1 Then";
if (_vvvvvv7[(int) (50)]==1) { 
 //BA.debugLineNum = 649;BA.debugLine="c44=inputBox(\"تعداد اولاد خاله های مادری را وا";
_vvvvvvvvvvvvvvv3 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد خاله های مادری را وارد کنید");
 };
 //BA.debugLineNum = 651;BA.debugLine="If w<>45 AND T(39)=0 Then T(51)=ynBox(\"آیا فوت";
if (_vvvvvvvvv1!=45 && _vvvvvv7[(int) (39)]==0) { 
_vvvvvv7[(int) (51)] = _vvvvvvvvvvvvvvvvvvvvvvv2("آیا فوت شده اولاد دایی مادری دارد؟");};
 //BA.debugLineNum = 652;BA.debugLine="If T(51)=1 Then";
if (_vvvvvv7[(int) (51)]==1) { 
 //BA.debugLineNum = 653;BA.debugLine="c45=inputBox(\"تعداد اولاد دایی های مادری را وا";
_vvvvvvvvvvvvvvv4 = _vvvvvvvvvvvvvvvvvvvvvvv0("تعداد اولاد دایی های مادری را وارد کنید");
 };
 };
 //BA.debugLineNum = 656;BA.debugLine="calc3";
_vvvvvvvvvvvvvvvvvvvvvvv6();
 //BA.debugLineNum = 657;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 659;BA.debugLine="Label2.Text = \"متاسفانه ارثی به شما تعلق نمیگیر";
mostCurrent._label2.setText((Object)("متاسفانه ارثی به شما تعلق نمیگیرد!"));
 //BA.debugLineNum = 660;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 663;BA.debugLine="End Sub";
return "";
}
public static int  _vvvvvvvvvvvvvvvvvvvvvvv2(String _msg) throws Exception{
int _ret = 0;
 //BA.debugLineNum = 116;BA.debugLine="Sub ynBox(msg As String) As Int";
 //BA.debugLineNum = 117;BA.debugLine="Dim ret As Int = Msgbox2(msg, \"\", \"بلی\", \"انصراف\"";
_ret = anywheresoftware.b4a.keywords.Common.Msgbox2(_msg,"","بلی","انصراف","خیر",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 118;BA.debugLine="If ret = DialogResponse.POSITIVE Then";
if (_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 119;BA.debugLine="Return 1";
if (true) return (int) (1);
 }else if(_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
 //BA.debugLineNum = 121;BA.debugLine="Return 0";
if (true) return (int) (0);
 }else if(_ret==anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 123;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return 0;
}
}
