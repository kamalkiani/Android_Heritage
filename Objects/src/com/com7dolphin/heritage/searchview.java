package com.com7dolphin.heritage;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class searchview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.com7dolphin.heritage.searchview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            if (BA.isShellModeRuntimeCheck(ba)) {
			    ba.raiseEvent2(null, true, "CREATE", true, "com.com7dolphin.heritage.searchview",
                    ba);
                return;
		    }
        }
        ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.Map _vvvvvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.collections.Map _vvvvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _vvvvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _vvvvvvvvvvvvvvvvv7 = null;
public int _min_limit = 0;
public int _max_limit = 0;
public Object _vvvvvvvvvvvvvvvvv0 = null;
public String _vvvvvvvvvvvvvvvvvv1 = "";
public com.com7dolphin.heritage.main _vvvvvvvvvvvvvvvvvv2 = null;
public com.com7dolphin.heritage.act2 _vvvvvvvvvvvvvvvvvv3 = null;
public com.com7dolphin.heritage.dbutils _vvvvvvvvvvvvvvvvvv4 = null;
public com.com7dolphin.heritage.act3 _vvvvvvvvvvvvvvvvvv5 = null;
public String  _vvvvvvvvvvvvvvvv0(anywheresoftware.b4a.objects.collections.List _li,String _full) throws Exception{
int _i = 0;
String _item = "";
 //BA.debugLineNum = 68;BA.debugLine="Private Sub AddItemsToList(li As List, full As Str";
 //BA.debugLineNum = 69;BA.debugLine="If li.IsInitialized = False Then Return";
if (_li.IsInitialized()==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 70;BA.debugLine="For i = 0 To li.Size - 1";
{
final int step59 = 1;
final int limit59 = (int) (_li.getSize()-1);
for (_i = (int) (0); (step59 > 0 && _i <= limit59) || (step59 < 0 && _i >= limit59); _i = ((int)(0 + _i + step59))) {
 //BA.debugLineNum = 71;BA.debugLine="Dim item As String";
_item = "";
 //BA.debugLineNum = 72;BA.debugLine="item = li.Get(i)";
_item = BA.ObjectToString(_li.Get(_i));
 //BA.debugLineNum = 73;BA.debugLine="If full.Length > MAX_LIMIT AND item.ToLowerCase.";
if (_full.length()>_max_limit && _item.toLowerCase().contains(_full)==__c.False) { 
 //BA.debugLineNum = 74;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 76;BA.debugLine="lv.AddSingleLine(li.Get(i))";
_vvvvvvvvvvvvvvvvv7.AddSingleLine(BA.ObjectToString(_li.Get(_i)));
 }
};
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvvvvvvvvvvvv1(anywheresoftware.b4a.objects.PanelWrapper _parent,int _left,int _top,int _width,int _height) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Public Sub AddToParent(Parent As Panel, Left As In";
 //BA.debugLineNum = 37;BA.debugLine="Parent.AddView(et, Left, Top, Width, 60dip)";
_parent.AddView((android.view.View)(_vvvvvvvvvvvvvvvvv6.getObject()),_left,_top,_width,__c.DipToCurrent((int) (60)));
 //BA.debugLineNum = 38;BA.debugLine="Parent.AddView(lv, Left, Top + et.Height, Width,";
_parent.AddView((android.view.View)(_vvvvvvvvvvvvvvvvv7.getObject()),_left,(int) (_top+_vvvvvvvvvvvvvvvvv6.getHeight()),_width,(int) (_height-_vvvvvvvvvvvvvvvvv6.getHeight()));
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private prefixList As Map";
_vvvvvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 5;BA.debugLine="Private substringList As Map";
_vvvvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 6;BA.debugLine="Private et As EditText";
_vvvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Private lv As ListView";
_vvvvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private MIN_LIMIT, MAX_LIMIT As Int";
_min_limit = 0;
_max_limit = 0;
 //BA.debugLineNum = 9;BA.debugLine="MIN_LIMIT = 1";
_min_limit = (int) (1);
 //BA.debugLineNum = 10;BA.debugLine="MAX_LIMIT = 4 'doesn't limit the words length. On";
_max_limit = (int) (4);
 //BA.debugLineNum = 11;BA.debugLine="Private mCallback As Object";
_vvvvvvvvvvvvvvvvv0 = new Object();
 //BA.debugLineNum = 12;BA.debugLine="Private mEventName As String";
_vvvvvvvvvvvvvvvvvv1 = "";
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public String  _et_textchanged(String _old,String _new) throws Exception{
String _str1 = "";
String _str2 = "";
 //BA.debugLineNum = 53;BA.debugLine="Private Sub et_TextChanged (Old As String, New As";
 //BA.debugLineNum = 54;BA.debugLine="lv.Clear";
_vvvvvvvvvvvvvvvvv7.Clear();
 //BA.debugLineNum = 55;BA.debugLine="If lv.Visible = False Then lv.Visible = True";
if (_vvvvvvvvvvvvvvvvv7.getVisible()==__c.False) { 
_vvvvvvvvvvvvvvvvv7.setVisible(__c.True);};
 //BA.debugLineNum = 56;BA.debugLine="If New.Length < MIN_LIMIT Then Return";
if (_new.length()<_min_limit) { 
if (true) return "";};
 //BA.debugLineNum = 57;BA.debugLine="Dim str1, str2 As String";
_str1 = "";
_str2 = "";
 //BA.debugLineNum = 58;BA.debugLine="str1 = New.ToLowerCase";
_str1 = _new.toLowerCase();
 //BA.debugLineNum = 59;BA.debugLine="If str1.Length > MAX_LIMIT Then";
if (_str1.length()>_max_limit) { 
 //BA.debugLineNum = 60;BA.debugLine="str2 = str1.SubString2(0, MAX_LIMIT)";
_str2 = _str1.substring((int) (0),_max_limit);
 }else {
 //BA.debugLineNum = 62;BA.debugLine="str2 = str1";
_str2 = _str1;
 };
 //BA.debugLineNum = 64;BA.debugLine="AddItemsToList(prefixList.Get(str2), str1)";
_vvvvvvvvvvvvvvvv0((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_vvvvvvvvvvvvvvvvv4.Get((Object)(_str2)))),_str1);
 //BA.debugLineNum = 65;BA.debugLine="AddItemsToList(substringList.Get(str2), str1)";
_vvvvvvvvvvvvvvvv0((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_vvvvvvvvvvvvvvvvv5.Get((Object)(_str2)))),_str1);
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 16;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 17;BA.debugLine="et.Initialize(\"et\")";
_vvvvvvvvvvvvvvvvv6.Initialize(ba,"et");
 //BA.debugLineNum = 18;BA.debugLine="et.Hint = \"جستجو...\"";
_vvvvvvvvvvvvvvvvv6.setHint("جستجو...");
 //BA.debugLineNum = 19;BA.debugLine="et.HintColor = Colors.Gray";
_vvvvvvvvvvvvvvvvv6.setHintColor(__c.Colors.Gray);
 //BA.debugLineNum = 20;BA.debugLine="et.TextColor = Colors.White";
_vvvvvvvvvvvvvvvvv6.setTextColor(__c.Colors.White);
 //BA.debugLineNum = 21;BA.debugLine="et.color = Colors.Black";
_vvvvvvvvvvvvvvvvv6.setColor(__c.Colors.Black);
 //BA.debugLineNum = 23;BA.debugLine="et.InputType = Bit.OR(et.INPUT_TYPE_TEXT, 0x00080";
_vvvvvvvvvvvvvvvvv6.setInputType(__c.Bit.Or(_vvvvvvvvvvvvvvvvv6.INPUT_TYPE_TEXT,(int) (0x00080000)));
 //BA.debugLineNum = 24;BA.debugLine="lv.Initialize(\"lv\")";
_vvvvvvvvvvvvvvvvv7.Initialize(ba,"lv");
 //BA.debugLineNum = 25;BA.debugLine="lv.SingleLineLayout.ItemHeight = 50dip";
_vvvvvvvvvvvvvvvvv7.getSingleLineLayout().setItemHeight(__c.DipToCurrent((int) (50)));
 //BA.debugLineNum = 26;BA.debugLine="lv.SingleLineLayout.Label.TextSize = 16";
_vvvvvvvvvvvvvvvvv7.getSingleLineLayout().Label.setTextSize((float) (16));
 //BA.debugLineNum = 27;BA.debugLine="lv.SingleLineLayout.Label.Width = 90%x";
_vvvvvvvvvvvvvvvvv7.getSingleLineLayout().Label.setWidth(__c.PerXToCurrent((float) (90),ba));
 //BA.debugLineNum = 28;BA.debugLine="lv.Visible = False";
_vvvvvvvvvvvvvvvvv7.setVisible(__c.False);
 //BA.debugLineNum = 29;BA.debugLine="prefixList.Initialize";
_vvvvvvvvvvvvvvvvv4.Initialize();
 //BA.debugLineNum = 30;BA.debugLine="substringList.Initialize";
_vvvvvvvvvvvvvvvvv5.Initialize();
 //BA.debugLineNum = 31;BA.debugLine="mCallback = Callback";
_vvvvvvvvvvvvvvvvv0 = _callback;
 //BA.debugLineNum = 32;BA.debugLine="mEventName = EventName";
_vvvvvvvvvvvvvvvvvv1 = _eventname;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public String  _lv_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.objects.IME _ime = null;
 //BA.debugLineNum = 41;BA.debugLine="Private Sub lv_ItemClick (Position As Int, Value A";
 //BA.debugLineNum = 42;BA.debugLine="et.Text = Value";
_vvvvvvvvvvvvvvvvv6.setText(_value);
 //BA.debugLineNum = 43;BA.debugLine="et.SelectionStart = et.Text.Length";
_vvvvvvvvvvvvvvvvv6.setSelectionStart(_vvvvvvvvvvvvvvvvv6.getText().length());
 //BA.debugLineNum = 44;BA.debugLine="Dim IME As IME";
_ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 45;BA.debugLine="IME.HideKeyboard";
_ime.HideKeyboard(ba);
 //BA.debugLineNum = 46;BA.debugLine="lv.Visible = False";
_vvvvvvvvvvvvvvvvv7.setVisible(__c.False);
 //BA.debugLineNum = 47;BA.debugLine="If SubExists(mCallback, mEventName & \"_ItemClick\"";
if (__c.SubExists(ba,_vvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvv1+"_ItemClick")) { 
 //BA.debugLineNum = 48;BA.debugLine="CallSub2(mCallback, mEventName & \"_ItemClick\", V";
__c.CallSubNew2(ba,_vvvvvvvvvvvvvvvvv0,_vvvvvvvvvvvvvvvvvv1+"_ItemClick",_value);
 };
 //BA.debugLineNum = 50;BA.debugLine="et.Text = \"\"";
_vvvvvvvvvvvvvvvvv6.setText((Object)(""));
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvvvvvvvvvvvv2(Object _index) throws Exception{
Object[] _obj = null;
 //BA.debugLineNum = 124;BA.debugLine="Public Sub SetIndex(Index As Object)";
 //BA.debugLineNum = 125;BA.debugLine="Dim obj() As Object";
_obj = new Object[(int) (0)];
{
int d0 = _obj.length;
for (int i0 = 0;i0 < d0;i0++) {
_obj[i0] = new Object();
}
}
;
 //BA.debugLineNum = 126;BA.debugLine="obj = Index";
_obj = (Object[])(_index);
 //BA.debugLineNum = 127;BA.debugLine="prefixList = obj(0)";
_vvvvvvvvvvvvvvvvv4.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (0)]));
 //BA.debugLineNum = 128;BA.debugLine="substringList = obj(1)";
_vvvvvvvvvvvvvvvvv5.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (1)]));
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public Object  _vvvvvvvvvvvvvvvvv3(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
long _starttime = 0L;
anywheresoftware.b4a.objects.collections.Map _noduplicates = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.List _li = null;
int _i = 0;
String _item = "";
int _start = 0;
int _count = 0;
String _str = "";
 //BA.debugLineNum = 82;BA.debugLine="Public Sub SetItems(Items As List) As Object";
 //BA.debugLineNum = 83;BA.debugLine="Dim startTime As Long";
_starttime = 0L;
 //BA.debugLineNum = 84;BA.debugLine="startTime = DateTime.Now";
_starttime = __c.DateTime.getNow();
 //BA.debugLineNum = 85;BA.debugLine="ProgressDialogShow2(\"Building index...\", False)";
__c.ProgressDialogShow2(ba,"Building index...",__c.False);
 //BA.debugLineNum = 86;BA.debugLine="Dim noDuplicates As Map";
_noduplicates = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 87;BA.debugLine="noDuplicates.Initialize";
_noduplicates.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="prefixList.Clear";
_vvvvvvvvvvvvvvvvv4.Clear();
 //BA.debugLineNum = 89;BA.debugLine="substringList.Clear";
_vvvvvvvvvvvvvvvvv5.Clear();
 //BA.debugLineNum = 90;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 91;BA.debugLine="Dim li As List";
_li = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 92;BA.debugLine="For i = 0 To Items.Size - 1";
{
final int step78 = 1;
final int limit78 = (int) (_items.getSize()-1);
for (_i = (int) (0); (step78 > 0 && _i <= limit78) || (step78 < 0 && _i >= limit78); _i = ((int)(0 + _i + step78))) {
 //BA.debugLineNum = 93;BA.debugLine="If i Mod 100 = 0 Then DoEvents";
if (_i%100==0) { 
__c.DoEvents();};
 //BA.debugLineNum = 94;BA.debugLine="Dim item As String";
_item = "";
 //BA.debugLineNum = 95;BA.debugLine="item = Items.Get(i)";
_item = BA.ObjectToString(_items.Get(_i));
 //BA.debugLineNum = 96;BA.debugLine="item = item.ToLowerCase";
_item = _item.toLowerCase();
 //BA.debugLineNum = 97;BA.debugLine="noDuplicates.Clear";
_noduplicates.Clear();
 //BA.debugLineNum = 98;BA.debugLine="For start = 0 To item.Length";
{
final int step84 = 1;
final int limit84 = _item.length();
for (_start = (int) (0); (step84 > 0 && _start <= limit84) || (step84 < 0 && _start >= limit84); _start = ((int)(0 + _start + step84))) {
 //BA.debugLineNum = 99;BA.debugLine="Dim count As Int";
_count = 0;
 //BA.debugLineNum = 100;BA.debugLine="count = MIN_LIMIT";
_count = _min_limit;
 //BA.debugLineNum = 101;BA.debugLine="Do While count <= MAX_LIMIT AND start + count <";
while (_count<=_max_limit && _start+_count<=_item.length()) {
 //BA.debugLineNum = 102;BA.debugLine="Dim str As String";
_str = "";
 //BA.debugLineNum = 103;BA.debugLine="str = item.SubString2(start, start + count)";
_str = _item.substring(_start,(int) (_start+_count));
 //BA.debugLineNum = 104;BA.debugLine="If noDuplicates.ContainsKey(str) = False Then";
if (_noduplicates.ContainsKey((Object)(_str))==__c.False) { 
 //BA.debugLineNum = 105;BA.debugLine="noDuplicates.Put(str, \"\")";
_noduplicates.Put((Object)(_str),(Object)(""));
 //BA.debugLineNum = 106;BA.debugLine="If start = 0 Then m = prefixList Else m = sub";
if (_start==0) { 
_m = _vvvvvvvvvvvvvvvvv4;}
else {
_m = _vvvvvvvvvvvvvvvvv5;};
 //BA.debugLineNum = 107;BA.debugLine="li = m.Get(str)";
_li.setObject((java.util.List)(_m.Get((Object)(_str))));
 //BA.debugLineNum = 108;BA.debugLine="If li.IsInitialized = False Then";
if (_li.IsInitialized()==__c.False) { 
 //BA.debugLineNum = 109;BA.debugLine="li.Initialize";
_li.Initialize();
 //BA.debugLineNum = 110;BA.debugLine="m.Put(str, li)";
_m.Put((Object)(_str),(Object)(_li.getObject()));
 };
 //BA.debugLineNum = 112;BA.debugLine="li.Add(Items.Get(i)) 'Preserve the original c";
_li.Add(_items.Get(_i));
 };
 //BA.debugLineNum = 114;BA.debugLine="count = count + 1";
_count = (int) (_count+1);
 }
;
 }
};
 }
};
 //BA.debugLineNum = 118;BA.debugLine="ProgressDialogHide";
__c.ProgressDialogHide();
 //BA.debugLineNum = 119;BA.debugLine="Log(\"Index time: \" & (DateTime.Now - startTime) &";
__c.Log("Index time: "+BA.NumberToString((__c.DateTime.getNow()-_starttime))+" ms ("+BA.NumberToString(_items.getSize())+" Items)");
 //BA.debugLineNum = 120;BA.debugLine="Return Array As Object(prefixList, substringList)";
if (true) return (Object)(new Object[]{(Object)(_vvvvvvvvvvvvvvvvv4.getObject()),(Object)(_vvvvvvvvvvvvvvvvv5.getObject())});
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return null;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
