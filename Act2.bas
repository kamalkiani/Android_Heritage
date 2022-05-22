Type=Activity
Version=5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

#Region  Module Attributes 
'#IgnoreWarnings: 1, 2, 3, 4, 5, 6
	'List of warnings that will be ignored in this module.
'#ExcludeFromDebugger: False
	'Whether to exclude this module from the debugger.
	'Debug information will not be added to this module.
	'Values: True or False
'#ExcludeFromLibrary: False
	'Whether to exclude this module during library compilation.
	'Values: True or False
#End Region

Sub Process_Globals
	Dim SQL1 As SQL
	Dim dir As String		
End Sub

Sub Globals
	Dim Cursor1 As Cursor	
	Dim index As Object
	Dim sv As SearchView
	' = = = = = = = menu variables = = = = = = = =
	Dim anOpenRightMenu, anCloseRightMenu As Animation
	Dim anOpenBlurPanel, anCloseBlurPanel As Animation
	Dim anOpenTopPanel, anCloseTopPanel As Animation
	Dim rightMenu As Panel
	Dim mainPanel As Panel
	Dim blurPanel As Panel
	Dim rightMenuIsOpen, topPanelIsOpen As Boolean
	Dim toolsList As ListView
	' = = = = = = = end of menu variables = = = = =
	Dim MyWebViewExtras As WebViewExtras
	Dim Javascript As String	
	Dim bitmap1 As Bitmap
	Dim WebView1 As WebView
	Dim titleLabel As Label
	Dim topPanel As Panel
	Dim favList As ListView
	Dim favTitle As Label
	Dim cd As CustomDialog2
	Dim pnl As Panel	
	Dim fontSize As String = "1em"
	Dim s1,s2,s3,s4 As RadioButton	
End Sub

Sub Activity_Resume
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	
End Sub

Sub SetTypeface(parent As Panel, t As Typeface)
   For Each v As View In parent
     If v Is Panel Then
       SetTypeface(v, t)
     Else If v Is Label Then
         Dim lbl As Label = v
         lbl.Typeface = t
     End If
   Next
End Sub

Sub Activity_Create (FirstTime As Boolean)	
	' = = =  Load data base = = = 
	dir=DBUtils.CopyDBFromAssets("heritage.db")
	If FirstTime Then
		SQL1.Initialize(dir, "heritage.db", False)
	End If
	' prepare layout 
	Activity.LoadLayout("layMain")
	titleLabel.Text = Main.pageTitle
	WebView1.LoadUrl("file:///android_asset" & Main.pageURL)
	createMenu
	createFavList
	createFont
	createSerch
	' set font
	Dim myFont As Typeface
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)
	SetTypeface(rightMenu,myFont)
End Sub

Sub createFont
	pnl.Initialize("pnl")
	pnl.Color = Colors.Transparent		
	s1.Initialize("s")
	s2.Initialize("s")
	s3.Initialize("s")
	s4.Initialize("s")
	s1.Text = "کوچک"
	s2.Text = "متوسط"
	s3.Text = "بزرگ"
	s4.Text = "خیلی بزرگ"
	pnl.AddView(s1,0,0,50%x,10%y)		
	pnl.AddView(s2,0,10%y,50%x,10%y)
	pnl.AddView(s3,0,20%y,50%x,10%y)
	pnl.AddView(s4,0,30%y,50%x,10%y)				
	cd.AddView(pnl, 55%x, 40%y)
End Sub

Sub createSerch
	sv.Initialize(Me, "sv")	
	Dim ind As List
	ind.Initialize
	Cursor1 = SQL1.ExecQuery("SELECT ind FROM search")	
    For i = 0 To Cursor1.RowCount - 1
		Cursor1.Position = i
		ind.Add(Cursor1.GetString("ind"))
	Next
	Cursor1.Close	
	index = sv.SetItems(ind)
	sv.SetIndex(index)	
End Sub

Sub createFavList
	favTitle.Initialize("favTitle")
	favTitle.Text="جهت حذف از کلیک مدت دار استفاده کنید"
	favTitle.TextColor = Colors.DarkGray
	favTitle.TextSize = 15
	favList.Initialize("favList")
	favList.TwoLinesAndBitmap.ImageView.SetLayout(84%x, 1%x, 15%x, 15%x)
	favList.TwoLinesAndBitmap.ItemHeight = 17%x
	favList.TwoLinesAndBitmap.Label.TextColor = Colors.White
	favList.TwoLinesAndBitmap.Label.Gravity = Gravity.RIGHT
	favList.TwoLinesAndBitmap.Label.SetLayout(0, 4%x, 80%x, 10%x)	
	favList.Color = Colors.Transparent
End Sub

'==============================================
'				Menu codes
'==============================================

Sub createMenu
	anOpenRightMenu.InitializeTranslate("RightMenuAnim", 0, 0, -50%x, 0)
	anOpenRightMenu.Duration = 300
	anCloseRightMenu.InitializeTranslate("RightMenuAnim", 0, 0, 50%x, 0)
	anCloseRightMenu.Duration = 300
	anOpenBlurPanel.InitializeAlpha("OpenBlurPanelAnim", 0, 1)
	anOpenBlurPanel.Duration = 300
	anCloseBlurPanel.InitializeAlpha("CloseBlurPanelAnim", 1, 0)
	anCloseBlurPanel.Duration = 300
	anOpenTopPanel.InitializeTranslate("TopPanelAnim", 0, 0, 0, 50%y)
	anOpenTopPanel.Duration = 300
	anCloseTopPanel.InitializeTranslate("TopPanelAnim", 0, 0, 0, -50%y)
	anCloseTopPanel.Duration = 300	
	rightMenuIsOpen = False
	topPanelIsOpen = False
	'right menu values :
	toolsList.TwoLinesAndBitmap.ImageView.SetLayout(38%x, 1%x, 12%x, 12%x)
	toolsList.TwoLinesAndBitmap.ImageView.Gravity = Gravity.FILL
	toolsList.TwoLinesAndBitmap.ItemHeight = 15%x
	toolsList.TwoLinesAndBitmap.Label.TextColor = Colors.White
	toolsList.TwoLinesAndBitmap.Label.Gravity = Gravity.LEFT
	toolsList.TwoLinesAndBitmap.Label.SetLayout(5%x, 3%x, 33%x, 13%x)
	toolsList.TwoLinesAndBitmap.Label.TextSize = 18
	toolsList.AddTwoLinesAndBitmap2("می پسندم", "", LoadBitmap(File.DirAssets , "addfav.png"), "addfav")
	toolsList.AddTwoLinesAndBitmap2("علاقه مندی ها", "", LoadBitmap(File.DirAssets , "fav.png"), "fav")
	toolsList.AddTwoLinesAndBitmap2("اندازه قلم", "", LoadBitmap(File.DirAssets , "font.png"), "font")
	toolsList.AddTwoLinesAndBitmap2("اشتراک", "", LoadBitmap(File.DirAssets , "share.png"), "share")
	toolsList.AddTwoLinesAndBitmap2("جستجو", "", LoadBitmap(File.DirAssets , "search.png"), "search")
	toolsList.AddTwoLinesAndBitmap2("درباره", "", LoadBitmap(File.DirAssets , "about.png"), "about")
End Sub

Sub OpenBlurPanelAnim_AnimationEnd
	blurPanel.Visible = True
End Sub

Sub CloseBlurPanelAnim_AnimationEnd
	blurPanel.Visible = False
End Sub

Sub rightMenuAnim_AnimationEnd
	If Sender = anOpenRightMenu Then
		rightMenu.left = 50%x
	Else If Sender = anCloseRightMenu Then
		rightMenu.left = 100%x
	End If	
End Sub

Sub TopPanelAnim_AnimationEnd
	If Sender = anOpenTopPanel Then
		topPanel.top = 0
	Else If Sender = anCloseTopPanel Then
		topPanel.top = -50%y
	End If	
End Sub

Sub menuImg_Click
	openRightMenu
End Sub

Sub homeImg_Click
	Activity.finish
End Sub

Sub blurPanel_Touch (Action As Int, X As Float, Y As Float)
	If Action = Activity.ACTION_DOWN Then
		If rightMenuIsOpen Then
			closeRightMenu
		Else If topPanelIsOpen Then
			closeTopPanel
		End If	
	End If
End Sub

Sub closeRightMenu
	rightMenuIsOpen = False
	EnableAll(mainPanel, True)
	anCloseBlurPanel.Start(blurPanel)
	anCloseRightMenu.Start(rightMenu)
End Sub

Sub openRightMenu
	rightMenuIsOpen = True
	EnableAll(mainPanel, False)
	blurPanel.Visible = True
	anOpenBlurPanel.Start(blurPanel)
	anOpenRightMenu.Start(rightMenu)
End Sub

Sub closeTopPanel
	topPanelIsOpen = False
	EnableAll(mainPanel, True)
	anCloseBlurPanel.Start(blurPanel)
	anCloseTopPanel.Start(topPanel)
End Sub

Sub openTopPanel
	topPanelIsOpen = True
	EnableAll(mainPanel, False)
	blurPanel.Visible = True
	anOpenBlurPanel.Start(blurPanel)
	anOpenTopPanel.Start(topPanel)
End Sub

'==============================================
'			End of menu codes
'==============================================

Sub EnableAll(p As Panel, Enabled As Boolean)
   For Each v As View In p
      If v Is Panel Then
         EnableAll(v, Enabled)
      Else
         v.Enabled = Enabled
      End If
   Next
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If rightMenuIsOpen = True Then
			closeRightMenu
			Return True 
		Else If topPanelIsOpen = True Then
			closeTopPanel
			Return True
		Else If WebView1.Url.SubString(WebView1.Url.IndexOf("#"))<> "#menu" Then
			Javascript="document.location.href = '#menu'"
			MyWebViewExtras.executeJavascript(WebView1, Javascript)
			Return True
		End If
	End If	
End Sub

Sub favList_ItemClick (Position As Int, Value As Object)
	Dim str, pageURL As String
	str = favList.GetItem(Position)
	titleLabel.Text = str.SubString2(0,str.IndexOf("-"))
	pageURL = str.SubString(str.IndexOf("-")+1)
	WebView1.LoadUrl(pageURL)
	closeTopPanel		
End Sub

Sub favList_ItemLongClick (Position As Int, Value As Object)
	If Msgbox2("از لیست حذف شود؟","توجه", "بلی", "", "خیر", Null) = DialogResponse.POSITIVE Then
		SQL1.ExecNonQuery("delete from favs where favinfo = '" & favList.GetItem(Position) & "'")
		buildFav
	Else
		Return
	End If
End Sub

Sub buildFav
	Dim str As String
	favList.Clear
	Cursor1 = SQL1.ExecQuery("SELECT * FROM favs")
	bitmap1.Initialize(File.DirAssets, "fav.png")
    For i = 0 To Cursor1.RowCount - 1
		Cursor1.Position = i
		str = Cursor1.GetString("favinfo")
		favList.AddTwoLinesAndBitmap2(str.SubString2(0,str.IndexOf("-")) &": "& str.SubString(str.IndexOf("#")+1) ,"", bitmap1, str)
	Next
	Cursor1.Close
End Sub

Sub toolsList_ItemClick (Position As Int, Value As Object)
	If Value = "fav" Then
		' fav clicked
		closeRightMenu				
		topPanel.RemoveAllViews
		topPanel.AddView(favTitle,2%x,1%x,96%x,7%y)
		topPanel.AddView(favList,0,7%y,100%x,43%y)
		buildFav	
		openTopPanel
	Else If Value = "addfav" Then
		' add fav clicked	
		closeRightMenu
		Dim myUrl As String = WebView1.Url
		SQL1.ExecNonQuery("insert into favs values('" & titleLabel.Text &"-"& myUrl & "')")
		ToastMessageShow("به لیست علاقه مندی اضافه شد",False)		
	Else If Value = "about" Then
		' about clicked
		titleLabel.Text = "درباره ما"
		WebView1.LoadUrl("file:///android_asset/data/about.html#menu")
		closeRightMenu
	Else If Value = "search" Then
		' search clicked
		closeRightMenu				
		topPanel.RemoveAllViews		
		sv.AddToParent(topPanel, 1%x, 1%y, 98%x, 49%y)				
		openTopPanel
	Else If Value = "share" Then
		' share clicked		
		Dim share1 As Intent
		share1.Initialize(share1.ACTION_SEND,"")
		share1.SetType("text/plain")
		share1.PutExtra("android.intent.extra.TEXT", "http://cafebazaar.ir/app/com.com7dolphin.heritage/")
		share1.WrapAsIntentChooser("Share via")
		StartActivity(share1)
	Else If Value = "font" Then
		' change font size
		closeRightMenu
		Dim ret As Int
		ret = cd.Show("تغییر اندازه قلم", "بلی", "خیر", "", Null)
		If ret = -1 Then		
			If s1.Checked Then 
				fontSize = "0.7em"
			Else If s2.Checked Then 
				fontSize = "1em"
			Else If s3.Checked Then 
				fontSize = "1.6em"
			Else If s4.Checked Then 
				fontSize = "2.2em"
		End If
			Javascript="document.getElementById(""myP"").style.fontSize=""" & fontSize & """"
			MyWebViewExtras.executeJavascript(WebView1, Javascript)			
		End If		
	End If	
End Sub

Sub sv_ItemClick(Value As String)	
	Dim str, pageURL As String
	Cursor1 = SQL1.ExecQuery("SELECT page FROM search where ind = '" & Value & "'")
	Cursor1.Position = 0
	str = Cursor1.GetString("page")
	Cursor1.Close	
	
	titleLabel.Text = str.SubString2(0,str.IndexOf("-"))
	pageURL = str.SubString(str.IndexOf("-")+1)
	WebView1.LoadUrl(pageURL)
	closeTopPanel	
End Sub

Sub ImageView2_Click
	Main.pageTitle = "ارث من"
	StartActivity(act3)
End Sub

Sub ImageView1_Click
	Dim p As PhoneIntents
    StartActivity(p.OpenBrowser("http://www.7dolphin.com"))	
End Sub
