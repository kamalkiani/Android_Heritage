Type=Activity
Version=5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals

End Sub

Sub Globals
	Private Label2 As Label
	Private titleLabel As Label
	Dim clv41 As CustomListView
	Dim T(52) As Int
	Dim WHP,QMN,CMO,PQN,WNP,CLQ,DON As Int
	Dim POW,CK,M1,M2,WSUM,WPSUM,PP,MSUM,KSUM,KPSUM As Int
	Dim W As Int
	Dim Q As Float
	Dim Q1,Q2,temp,sum,xsum As Float
	Dim sbList As List
	Dim Np,Nd,Mp,Md,Z As Int
	Dim L3,L4,L5,b3k,b3b,b2k,b2b,b1k,b1b,a3k,a3b,a2k,a2b,a1k,a1b As Int
	Dim c22,c23,c24,c25,c26,c27,c28,c29,c30,c31,c32,c33,c34,c35,c36,c37,c38,c39,c40,c41,c42,c43,c44,c45 As Int
	Private mainPanel As Panel
End Sub



Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("LayErth")
	titleLabel.Text = Main.pageTitle
	sbList.Initialize
	
	Dim myFont As Typeface
	myFont= Typeface.LoadFromAssets("BYekan.ttf")
	SetTypeface(mainPanel,myFont)	
End Sub

Sub SetTypeface(parent As Panel, tt As Typeface)
   For Each v As View In parent
     If v Is Panel Then
       SetTypeface(v, tt)
     Else If v Is Label Then
         Dim lbl As Label = v
         lbl.Typeface = tt
     End If
   Next
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub homeImg_Click
	Activity.finish
End Sub

Sub menuImg_Click
	ToastMessageShow("منو غیر فعال است",False)
End Sub

Sub Button1_Click
	Label2.Text = ""
	For i=0 To 51
		T(i) = 0
	Next
	W=-1
	Q=100
	Q1=0
	Q2=0
	temp=0
	sum=0
	xsum=0
	POW=0
	WHP=0
	QMN=0
	CMO=0
	PQN=0
	WNP=0
	CLQ=0
	DON=0
	CK=0
	MSUM=0
	WPSUM=0
	WSUM=0
	PP=0
	M1=0
	M2=0
	KSUM=0
	KPSUM=0
	startCalc	
End Sub

Sub inputBox(msg As String) As Int
	Dim Id As InputDialog	
	Id.InputType = Id.INPUT_TYPE_NUMBERS
	Id.Input = ""
	Id.Hint = "تعداد پیش فرض 1"
	Id.HintColor = Colors.Gray
	Dim ret As Int = Id.show("", msg, "تایید", "انصراف", "", Null)
	If ret = DialogResponse.POSITIVE Then
		If Id.Input = "" Then
			Return 1
		Else
			Return Id.Input
		End If
	Else If ret = DialogResponse.CANCEL Then
		Activity.Finish
	End If
End Sub

Sub ynBox(msg As String) As Int
	Dim ret As Int = Msgbox2(msg, "", "بلی", "انصراف", "خیر", Null)
	If ret = DialogResponse.POSITIVE Then
		Return 1
	Else If ret = DialogResponse.NEGATIVE Then
		Return 0
	Else If ret = DialogResponse.CANCEL Then
		Activity.Finish		
	End If
End Sub

Sub selectBox(msg As String)As String
	Dim pnl As Panel
	pnl.Initialize("pnl")
	pnl.Color = Colors.Transparent
	clv41.Initialize(Me, "clv41")
	pnl.AddView(clv41.AsView, 0, 3%y, 85%x, 44%y)
	For i = 0 To sbList.Size-1
		clv41.Add(CreateListItem41(sbList.Get(i), clv41.AsView.Width, 40dip), 40dip, sbList.Get(i))
	Next	
	Dim cd As CustomDialog2
	cd.AddView(pnl, 85%x, 50%y)	
	Dim ret As Int = cd.Show(msg, "تایید", "انصراف", "هیچکدام", Null)
		If ret = DialogResponse.POSITIVE Then
		Dim p As Panel
		For i = 0 To clv41.GetSize-1
			p = clv41.GetPanel(i)
			If p.Tag = "blue" Then
				Return clv41.GetValue(i)
			End If
		Next
		Return "none"
	Else If ret = DialogResponse.NEGATIVE Then
		Return "none"
	Else If ret = DialogResponse.CANCEL Then
		Activity.Finish
	End If
End Sub

Sub CreateListItem41(Text As String, Width As Int, Height As Int) As Panel
	Dim p As Panel
	p.Initialize("p41")
	p.Color = Colors.Black
	p.Tag = "black"
	Dim lbl As Label
	lbl.Initialize("")
	lbl.Gravity = Bit.OR(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL)
	lbl.Text = Text
	lbl.TextSize = 20
	lbl.TextColor = Colors.White
	p.AddView(lbl, 0, 0, Width-2dip, Height-2dip) 'view #0
	Return p
End Sub

Sub p41_Click
	Dim pnl As Panel
	For i = 0 To clv41.GetSize-1
		pnl = clv41.GetPanel(i)
		pnl.Color = Colors.Black
		pnl.Tag = "black"
	Next
	Dim index As Int
	index = clv41.GetItemFromView(Sender)
	pnl = clv41.GetPanel(index)
	pnl.Color = Colors.blue
	pnl.Tag = "blue"
End Sub

Sub startCalc
	' T1 -> T7
	T(1)=ynBox("آیا پدر شخص فوت شده در قید حیات است؟")
	T(2)=ynBox("آیا مادر شخص فوت شده در قید حیات است؟")
	T(3)=ynBox("آیا فوت شده فرزند دارد؟")
	If T(3)= 1 Then
		Np=inputBox("تعداد فرزندان پسر فوت شده را وارد کنید")
		Nd=inputBox("تعداد فرزندان دختر فوت شده را وارد کنید")
	Else
		T(4)=ynBox("آیا فوت شده فرزند فرزند دارد؟")
		If T(4)= 1 Then
			Mp=inputBox("تعداد فرزندان فرزند پسری فوت شده را وارد کنید")
			Md=inputBox("تعداد فرزندان فرزند دختری فوت شده را وارد کنید")
		End If
	End If
	T(5)=ynBox("آیا فوت شده مرد است؟")
	If T(5)=1 Then
		T(6)=ynBox("آیا فوت شده همسر عقدی دائم یا زنی که طلاق رجعی شده و مرد در زمان عده وی فوت شده دارد؟")
		If T(6)=1 Then
			Z=inputBox("تعداد همسران عقدی دائم فوت شده را وارد کنید")
		End If
	Else
		T(7)=ynBox("آیا فوت شده به عقد دائم مردی درآمده است؟")
	End If

	' مشخص نمودن نسبت T8 , W
	sbList.Clear
	If T(1)=1 Then
		sbList.Add("پدر")
	End If
	If T(2)=1 Then
		sbList.Add("مادر")
	End If
	If T(3)=1 Then
		sbList.Add("فرزند پسر")
		sbList.Add("فرزند دختر")
	End If
	If T(4)=1 Then
		sbList.Add("فرزند فرزند پسر")
		sbList.Add("فرزند فرزند دختر")
	End If	
	If T(5)=1  AND T(6)=1 Then
		sbList.Add("زن عقدی دائم")
	Else If T(5)=0 AND T(7)=1 Then
		sbList.Add("شوهر عقدی")
	End If
	
	If sbList.Size = 0 Then
		T(8)=0
	Else		
		T(8)=1
		Dim sb As String = selectBox("نسبت شما با فوت شده کدامیک از موارد زیر است؟")
		If sb = "پدر" Then
			W = 0
		Else If sb = "مادر" Then 
			W = 1
		Else If sb = "فرزند پسر" Then 
			W = 2
		Else If sb = "فرزند دختر" Then 
			W = 3
		Else If sb = "فرزند فرزند پسر" Then 
			W = 4
		Else If sb = "فرزند فرزند دختر" Then 
			W = 5
		Else If sb = "زن عقدی دائم" Then 
			W = 6
		Else If sb = "شوهر عقدی" Then 
			W = 7
		Else If sb = "none" Then 
			T(8)=0
		End If
	End If

	If T(8) = 0 AND (T(1)=1 OR T(2)=1 OR T(3)=1 OR T(4)=1) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return 
	End If

	'مشخص نمودن مقدار Q1, Q2
	' Q2 : سهم زن و شوهر
	' Q1 : باقیمانده سهم

		If T(6)=1 AND (T(3)=1 OR (T(3)=0 AND T(4)=1)) Then			
				Q2=Q/(8*Z)
				Q1=Q-Q2
		Else If T(6)=1 AND T(3)=0 AND T(4)=0 Then
				Q2=Q/(4*Z)
				Q1=Q-Q2
		Else If T(7)=1 AND (T(3)=1 OR (T(3)=0 AND T(4)=1)) Then
			Q2=Q/4
			Q1=Q-Q2
		Else If T(7)=1 AND T(3)=0 AND T(4)=0 Then
			Q2=Q/2
			Q1=Q-Q2
		Else
			Q1=100
			Q2=0
		End If	
		
	If W=6  OR W=7 Then 
		Label2.Text= NumberFormat(Q2,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	End If
	
	' مشخص نمودن طبقه	

	If T(8) = 1 Then
		' طبقه اول
		calc1
		Return
	End If
	
	T(10)=ynBox("آیا شما از وارثان درجه دوم (پدربزرگ،مادربزرگ،خواهر،برادر،اولادخواهر،اولادبرادر) شخص فوت شده هستید؟")
	
	If T(8) = 0 AND T(10)=1 Then
		' طبقه دوم
		' مشخص نمودن نسبت W
		sbList.Clear
		sbList.Add("پدربزرگ پدری")
		sbList.Add("پدربزرگ مادری")
		sbList.Add("مادربزرگ پدری")
		sbList.Add("مادربزرگ مادری")
		sbList.Add("برادر که پدر و مادر یکسان هستند")
		sbList.Add("خواهر که پدر و مادر یکسان هستند")
		sbList.Add("برادر که تنها پدر یکسان است")
		sbList.Add("خواهر که تنها پدر یکسان است")
		sbList.Add("برادر که تنها مادر یکسان است")
		sbList.Add("خواهر که تنها مادر یکسان است")
		sbList.Add("اولاد برادر که پدر و مادر یکسان است")
		sbList.Add("اولاد خواهر که پدر و مادر یکسان است")
		sbList.Add("اولاد برادر که تنها پدر یکسان است")
		sbList.Add("اولاد خواهر که تنها پدر یکسان است")
		sbList.Add("اولاد برادر که تنها مادر یکسان است")
		sbList.Add("اولاد خواهر که تنها مادر یکسان است")
		
		sb = selectBox("نسبت شما با فوت شده کدامیک از موارد زیر است؟")
		If sb = "پدربزرگ پدری" Then
			W = 8
			T(11)=1
		Else If sb = "پدربزرگ مادری" Then 
			W = 81
			T(19)=1
		Else If sb = "مادربزرگ پدری" Then 
			W = 9
			T(12)=1
		Else If sb = "مادربزرگ مادری" Then 
			W = 91
			T(20)=1
		Else If sb = "برادر که پدر و مادر یکسان هستند" Then 
			W = 10
			T(13)=1
		Else If sb = "خواهر که پدر و مادر یکسان هستند" Then 
			W = 11
			T(14)=1
		Else If sb = "برادر که تنها پدر یکسان است" Then 
			W = 12
			T(15)=1
		Else If sb = "خواهر که تنها پدر یکسان است" Then 
			W = 13
			T(16)=1
		Else If sb = "برادر که تنها مادر یکسان است" Then
			W = 14
			T(17)=1
		Else If sb = "خواهر که تنها مادر یکسان است" Then 
			W = 15
			T(18)=1
		Else If sb = "اولاد برادر که پدر و مادر یکسان است" Then 
			W = 16
			T(21)=1
		Else If sb = "اولاد خواهر که پدر و مادر یکسان است" Then 
			W = 17
			T(22)=1
		Else If sb = "اولاد برادر که تنها پدر یکسان است" Then 
			W = 18
			T(23)=1
		Else If sb = "اولاد خواهر که تنها پدر یکسان است" Then 
			W = 19
			T(24)=1
		Else If sb = "اولاد برادر که تنها مادر یکسان است" Then 
			W = 20
			T(25)=1
		Else If sb = "اولاد خواهر که تنها مادر یکسان است" Then 
			W = 21
			T(26)=1
		Else If sb = "none" Then
			T(10)=0			
		End If		
		
		If T(10)=1 Then
			If w<>8 Then T(11)=ynBox("آیا پدربزرگ پدری شخص فوت شده در قید حیات است؟")
			If w<>9 Then T(12)=ynBox("آیا مادربزرگ پدری شخص فوت شده در قید حیات است؟")
			If w<>10 Then T(13)=ynBox("آیا فوت شده برادری با پدر و مادر یکسان دارد؟")
			If T(13)=1 Then
				a1b = inputBox("تعداد برادران با پدر و مادر یکسان را وارد کنید")
			End If 
			If w<>11 Then T(14)=ynBox("آیا فوت شده خواهری با پدر و مادر یکسان دارد؟")
			If T(14)=1 Then
				a1k = inputBox("تعداد خواهران با پدر و مادر یکسان را وارد کنید")
			End If 
			If w<>12 Then T(15)=ynBox("آیا فوت شده برادری که تنها پدر یکسان دارند، دارد؟")
			If T(15)=1 Then
				a2b = inputBox("تعداد برادران که تنها پدر یکسان دارند را وارد کنید")
			End If 
			If w<>13 Then T(16)=ynBox("آیا فوت شده خواهری که تنها پدر یکسان دارند، دارد؟")
			If T(16)=1 Then
				a2k = inputBox("تعداد خواهران که تنها پدر یکسان دارند را وارد کنید")
			End If 
			If w<>14 Then T(17)=ynBox("آیا فوت شده برادری که تنها مادر یکسان دارند، دارد؟")
			If T(17)=1 Then
				a3b = inputBox("تعداد برادران که تنها مادر یکسان دارند را وارد کنید")
			End If 
			If w<>15 Then T(18)=ynBox("آیا فوت شده خواهری که تنها مادر یکسان دارند، دارد؟")
			If T(18)=1 Then
				a3k = inputBox("تعداد خواهران که تنها مادر یکسان دارند را وارد کنید")
			End If 
			If w<>81 Then T(19)=ynBox("آیا پدربزرگ مادری شخص فوت شده در قید حیات است؟")
			If w<>91 Then T(20)=ynBox("آیا مادربزرگ مادری شخص فوت شده در قید حیات است؟")			
			If w<>16 AND T(13)=0 Then T(21)=ynBox("آیا فوت شده اولاد برادر  با پدر و مادر یکسان دارد؟")
			If T(21)=1 Then
				b1b = inputBox("تعداد اولاد برادر  با پدر و مادر یکسان را وارد کنید")
			End If 
			If w<>17 AND T(14)=0 Then T(22)=ynBox("آیا فوت شده اولاد خواهر با پدر و مادر یکسان دارد؟")
			If T(22)=1 Then
				b1k = inputBox("تعداد اولاد خواهر  با پدر و مادر یکسان را وارد کنید")
			End If 
			If w<>18 AND T(15)=0 Then T(23)=ynBox("آیا فوت شده اولاد برادر  که تنها پدر یکسان دارند، دارد؟")
			If T(23)=1 Then
				b2b = inputBox("تعداد اولاد برادر  که تنها پدر یکسان دارند را وارد کنید")
			End If 
			If w<>19 AND T(16)=0 Then T(24)=ynBox("آیا فوت شده اولاد خواهر  که تنها پدر یکسان دارند، دارد؟")
			If T(24)=1 Then
				b2k = inputBox("تعداد اولاد خواهر  که تنها پدر یکسان دارند را وارد کنید")
			End If 
			If w<>20 AND T(17)=0 Then T(25)=ynBox("آیا فوت شده اولاد برادر که تنها مادر یکسان دارند، دارد؟")
			If T(25)=1 Then
				b3b = inputBox("تعداد اولاد برادر  که تنها مادر یکسان دارند را وارد کنید")
			End If 
			If w<>21 AND T(18)=0 Then T(26)=ynBox("آیا فوت شده اولاد خواهر  که تنها مادر یکسان دارند، دارد؟")
			If T(26)=1 Then
				b3k = inputBox("تعداد اولاد خواهر  که تنها مادر یکسان دارند را وارد کنید")
			End If 
			L3 = 2*a1b + a1k
			L4 = 2*a2b + a2k
			L5 = 2*a3b + a3k
			calc2
			Return
		End If
	End If
	
	If T(8) = 0 AND T(10)=0 Then
		' طبقه سوم
		T(27) = ynBox("آیا شما از وراث طبقه سوم (عمو،عمه،دایی،خاله یا اولاد آنها) هستید؟")
				
		Dim KK As Int =ynBox("آیا یکی از وارثان درجه دوم شخص فوت شده در قید حیات است؟ (پدربزرگ،مادربزرگ،خواهر،برادر،اولادخواهر،اولادبرادر)")
		If T(27)=1 AND (isOne(1,4) OR isOne(11,26) OR KK=1 ) Then
			Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
			Return
		End If
		If isZero(1,4) AND isZero(11,26) Then
			POW = 1
		End If
		If T(27)=1 AND POW = 1 Then
			sbList.Clear
			sbList.Add("عموی پدری و مادری")
			sbList.Add("عمه پدری و مادری")
			sbList.Add("عموی پدری")
			sbList.Add("عمه پدری")
			sbList.Add("عموی مادری")
			sbList.Add("عمه مادری")
			sbList.Add("خاله پدری و مادری")
			sbList.Add("دایی پدری و مادری")
			sbList.Add("خاله پدری")
			sbList.Add("دایی پدری")
			sbList.Add("خاله مادری")
			sbList.Add("دایی مادری")
			sbList.Add("اولاد عموی پدری و مادری")
			sbList.Add("اولاد عمه پدری و مادری")
			sbList.Add("اولاد عموی پدری")
			sbList.Add("اولاد عمه پدری")
			sbList.Add("اولاد عموی مادری")
			sbList.Add("اولاد عمه مادری")
			sbList.Add("اولاد خاله پدری و مادری")
			sbList.Add("اولاد دایی پدری و مادری")
			sbList.Add("اولاد خاله پدری")
			sbList.Add("اولاد دایی پدری")
			sbList.Add("اولاد خاله مادری")
			sbList.Add("اولاد دایی مادری")			
			sb = selectBox("نسبت شما با فوت شده کدامیک از موارد زیر است؟")
			If sb = "عموی پدری و مادری" Then
				W = 22
				T(28)=1
			Else If sb = "عمه پدری و مادری" Then 
				W = 23
				T(29)=1
			Else If sb = "عموی پدری" Then 
				W = 24
				T(30)=1
			Else If sb = "عمه پدری" Then 
				W = 25
				T(31)=1
			Else If sb = "عموی مادری" Then 
				W = 26
				T(32)=1
			Else If sb = "عمه مادری" Then 
				W = 27
				T(33)=1
			Else If sb = "خاله پدری و مادری" Then 
				W = 28
				T(34)=1
			Else If sb = "دایی پدری و مادری" Then 
				W = 29
				T(35)=1
			Else If sb = "خاله پدری" Then
				W = 30
				T(36)=1
			Else If sb = "دایی پدری" Then 
				W = 31
				T(37)=1
			Else If sb = "خاله مادری" Then 
				W = 32
				T(38)=1
			Else If sb = "دایی مادری" Then 
				W = 33
				T(39)=1
			Else If sb = "اولاد عموی پدری و مادری" Then
				W = 34
				T(40)=1
			Else If sb = "اولاد عمه پدری و مادری" Then 
				W = 35
				T(41)=1
			Else If sb = "اولاد عموی پدری" Then 
				W = 36
				T(42)=1
			Else If sb = "اولاد عمه پدری" Then 
				W = 37
				T(43)=1				
			Else If sb = "اولاد عموی مادری" Then 
				W = 38
				T(44)=1
			Else If sb = "اولاد عمه مادری" Then 
				W = 39
				T(45)=1
			Else If sb = "اولاد خاله پدری و مادری" Then 
				W = 40
				T(46)=1
			Else If sb = "اولاد دایی پدری و مادری" Then 
				W = 41
				T(47)=1
			Else If sb = "اولاد خاله پدری" Then
				W = 42
				T(48)=1
			Else If sb = "اولاد دایی پدری" Then 
				W = 43
				T(49)=1
			Else If sb = "اولاد خاله مادری" Then 
				W = 44
				T(50)=1
			Else If sb = "اولاد دایی مادری" Then 
				W = 45
				T(51)=1
			Else If sb = "none" Then
				T(27)=0			
			End If		
		End If
		If T(27)=1 AND POW=1 Then
			If w<>22 Then T(28)=ynBox("آیا فوت شده عموی پدری و مادری دارد؟")
			If T(28)=1 Then
				c22=inputBox("تعداد عموهای پدری و مادری را وارد کنید")
			End If
			If w<>23 Then T(29)=ynBox("آیا فوت شده عمه پدری و مادری دارد؟")
			If T(29)=1 Then
				c23=inputBox("تعداد عمه های پدری و مادری را وارد کنید")
			End If
			If w<>24 Then T(30)=ynBox("آیا فوت شده عموی پدری  دارد؟")
			If T(30)=1 Then
				c24=inputBox("تعداد عموهای پدری  را وارد کنید")
			End If
			If w<>25 Then T(31)=ynBox("آیا فوت شده عمه پدری  دارد؟")
			If T(31)=1 Then
				c25=inputBox("تعداد عمه های پدری  را وارد کنید")
			End If
			If w<>26 Then T(32)=ynBox("آیا فوت شده عموی مادری  دارد؟")
			If T(32)=1 Then
				c26=inputBox("تعداد عموهای مادری  را وارد کنید")
			End If
			If w<>27 Then T(33)=ynBox("آیا فوت شده عمه مادری  دارد؟")
			If T(33)=1 Then
				c27=inputBox("تعداد عمه های مادری  را وارد کنید")
			End If
			If w<>28 Then T(34)=ynBox("آیا فوت شده خاله پدر و مادری دارد؟")
			If T(34)=1 Then
				c28=inputBox("تعداد خاله های پدری و مادری را وارد کنید")
			End If
			If w<>29 Then T(35)=ynBox("آیا فوت شده دایی پدر و مادری دارد؟")
			If T(35)=1 Then
				c29=inputBox("تعداد دایی های پدری و مادری را وارد کنید")
			End If
			If w<>30 Then T(36)=ynBox("آیا فوت شده خاله پدری دارد؟")
			If T(36)=1 Then
				c30=inputBox("تعداد خاله های پدری را وارد کنید")
			End If
			If w<>31 Then T(37)=ynBox("آیا فوت شده دایی پدری دارد؟")
			If T(37)=1 Then
				c31=inputBox("تعداد دایی های پدری را وارد کنید")
			End If
			If w<>32 Then T(38)=ynBox("آیا فوت شده خاله مادری دارد؟")
			If T(38)=1 Then
				c32=inputBox("تعداد خاله های مادری را وارد کنید")
			End If
			If w<>33 Then T(39)=ynBox("آیا فوت شده دایی مادری دارد؟")
			If T(39)=1 Then
				c33=inputBox("تعداد دایی های مادری را وارد کنید")
			End If
			
			If (w=34 AND T(28)=1) OR (w=35 AND T(29)=1) OR (w=36 AND T(30)=1) OR _
				(w=37 AND T(31)=1) OR (w=38 AND T(32)=1) OR (w=39 AND T(33)=1) OR _
				(w=40 AND T(34)=1) OR (w=41 AND T(35)=1) OR (w=42 AND T(36)=1) OR _
				(w=43 AND T(37)=1) OR (w=44 AND T(38)=1) OR (w=45 AND T(39)=1) Then
				Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
				Return
			End If
			
			If T(27)=1 AND POW=1 Then
			If w<>34 AND T(28)=0 Then T(40)=ynBox("آیا فوت شده اولاد عموی پدری و مادری دارد؟")
			If T(40)=1 Then
				c34=inputBox("تعداد اولاد عموهای پدری و مادری را وارد کنید")
			End If
			If w<>35 AND T(29)=0 Then T(41)=ynBox("آیا فوت شده اولاد عمه پدری و مادری دارد؟")
			If T(41)=1 Then
				c35=inputBox("تعداد اولاد عمه های پدری و مادری را وارد کنید")
			End If
			If w<>36 AND T(30)=0 Then T(42)=ynBox("آیا فوت شده اولاد عموی پدری  دارد؟")
			If T(42)=1 Then
				c36=inputBox("تعداد اولاد عموهای پدری  را وارد کنید")
			End If
			If w<>37 AND T(31)=0 Then T(43)=ynBox("آیا فوت اولاد شده عمه پدری  دارد؟")
			If T(43)=1 Then
				c37=inputBox("تعداد اولاد عمه های پدری  را وارد کنید")
			End If
			If w<>38 AND T(32)=0 Then T(44)=ynBox("آیا فوت شده اولاد عموی مادری  دارد؟")
			If T(44)=1 Then
				c38=inputBox("تعداد اولاد عموهای مادری  را وارد کنید")
			End If
			If w<>39 AND T(33)=0 Then T(45)=ynBox("آیا فوت شده اولاد عمه مادری  دارد؟")
			If T(45)=1 Then
				c39=inputBox("تعداد اولاد عمه های مادری  را وارد کنید")
			End If
			If w<>40 AND T(34)=0 Then T(46)=ynBox("آیا فوت شده اولاد خاله پدر و مادری دارد؟")
			If T(46)=1 Then
				c40=inputBox("تعداد اولاد خاله های پدری و مادری را وارد کنید")
			End If
			If w<>41 AND T(35)=0 Then T(47)=ynBox("آیا فوت شده اولاد دایی پدر و مادری دارد؟")
			If T(47)=1 Then
				c41=inputBox("تعداد اولاد دایی های پدری و مادری را وارد کنید")
			End If
			If w<>42 AND T(36)=0 Then T(48)=ynBox("آیا فوت شده اولاد خاله پدری دارد؟")
			If T(48)=1 Then
				c42=inputBox("تعداد اولاد خاله های پدری را وارد کنید")
			End If
			If w<>43 AND T(37)=0 Then T(49)=ynBox("آیا فوت شده اولاد دایی پدری دارد؟")
			If T(49)=1 Then
				c43=inputBox("تعداد اولاد دایی های پدری را وارد کنید")
			End If
			If w<>44 AND T(38)=0 Then T(50)=ynBox("آیا فوت شده اولاد خاله مادری دارد؟")
			If T(50)=1 Then
				c44=inputBox("تعداد اولاد خاله های مادری را وارد کنید")
			End If
			If w<>45 AND T(39)=0 Then T(51)=ynBox("آیا فوت شده اولاد دایی مادری دارد؟")
			If T(51)=1 Then
				c45=inputBox("تعداد اولاد دایی های مادری را وارد کنید")
			End If
			End If
			calc3
			Return
		Else
			Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
			Return
		End If							
	End If
End Sub

Sub calc1
	' محاسبات طبقه اول
	If (W=4 OR W=5) AND T(3)=1 Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return
	Else If W=0 AND T(2)=0 AND T(3)=0 AND T(4)=0 Then
		Label2.Text = NumberFormat(Q1,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND T(1)=0 AND T(3)=0 AND T(4)=0 Then
		Label2.Text = NumberFormat(Q1,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=2 AND T(1)=0 AND T(2)=0 AND Np=1 AND Nd=0 Then
		Label2.Text = NumberFormat(Q1,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=3 AND T(1)=0 AND T(2)=0 AND Np=0 AND Nd=1 Then
		Label2.Text = NumberFormat(Q1,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If (W=2 OR W=3) AND (T(3)=1 AND T(1)=0 AND T(2)=0) Then
		Dim l1 As Int = 2*Np + Nd
		If W=2 Then
			temp = (2/l1)*Q1
		Else If W=3 Then
			temp = Q1/l1
		End If		 
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=1 AND T(3)=1 AND (Np>0 OR Nd>1 ) Then
		temp = (1/6)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND T(1)=1 AND T(3)=1 AND (Np>0 OR Nd>1 ) Then
		temp = (1/6)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=2 AND T(1)=1 AND T(2)=1 AND T(3)=1 Then
		temp = (4/6)*Q1*(2/(2*Np+Nd))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=3 AND T(1)=1 AND T(2)=1 AND T(3)=1 AND (Np>0 OR Nd>1) Then
		temp = (4/6)*Q1*(1/(2*Np+Nd))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=0 AND T(3)=1 AND Np>0 Then
		temp = (1/6)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=0 AND T(3)=1 AND Np>0 Then
		temp = (1/6)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=2 AND ( (T(1)=1 AND T(2)=0 AND Np>0) OR (T(1)=0 AND T(2)=1 AND Np>0)  ) Then
		temp = (10*Q1)/((12*Np)+(6*Nd))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=3 AND ( (T(1)=1 AND T(2)=0 AND Np>0) OR (T(1)=0 AND T(2)=1 AND Np>0)  ) Then
		temp = (5*Q1)/((12*Np)+(6*Nd))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=0 AND T(2)=0 AND T(3)=1 AND Np=0 AND Nd=1 Then
		temp = (1/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=0 AND T(3)=1 AND Np=0 AND Nd=1 Then
		temp = (1/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=3 AND T(3)=1 AND Np=0 AND Nd=1 AND ((T(2)=0 AND T(1)=1)OR(T(2)=1 AND T(1)=0)) Then
		temp = (3/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=0 AND T(2)=0 AND T(3)=1 AND Np=0 AND Nd>1 Then
		temp = (1/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=0 AND T(3)=1 AND Np=0 AND Nd>1 Then
		temp = (1/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=3 AND T(3)=1 AND Np=0 AND Nd>1 AND ((T(2)=0 AND T(1)=1)OR(T(2)=1 AND T(1)=0)) Then
		temp = (4/(5*Nd))*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=4 AND T(1)=0 AND T(2)=0 AND T(3)=0 AND T(4)=1 Then
		temp = (2*Q1)/(2*Mp+Md)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=5 AND T(1)=0 AND T(2)=0 AND T(3)=0 AND T(4)=1 Then
		temp = Q1/(2*Mp+Md)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND ( Mp>0 OR Md>1 ) Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=1 AND T(3)=0 AND ( Mp>0 OR Md>1 ) Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=4 AND T(1)=1 AND T(2)=1 AND T(3)=0 AND T(4)=1 Then
		temp = (8*Q1)/((12*Mp)+(6*Md))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=5 AND T(1)=1 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND ( Mp>0 OR Md>1 ) Then
		temp = (4*Q1)/((12*Mp)+(6*Md))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=0 AND T(3)=0 AND T(4)=1 AND Mp>0 Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND T(1)=0 AND T(3)=0 AND T(4)=1 AND Mp>0 Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=4 AND T(1)=1 AND T(2)=0 AND T(3)=0 AND T(4)=1 AND Mp>0 Then
		temp = (10*Q1)/((12*Mp)+(6*Md))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=4 AND T(1)=0 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp>0 Then
		temp = (10*Q1)/((12*Mp)+(6*Md))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=5 AND ( (T(1)=1 AND T(2)=0 AND Mp>0) OR (T(1)=0 AND T(2)=1 AND Mp>0) ) Then
		temp = (5*Q1)/((12*Mp)+(6*Md))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=0 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 Then
		temp = (1/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=0 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 Then
		temp = (1/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=5 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND ((T(2)=0 AND T(1)=1)OR(T(2)=1 AND T(1)=0)) Then
		temp = (3/4)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=0 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md>1 Then
		temp = (1/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=0 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md>1 Then
		temp = (1/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=5 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md>1 AND ((T(2)=0 AND T(1)=1)OR(T(2)=1 AND T(1)=0)) Then
		temp = (4*Q1)/(5*Md)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	End If

	' محاسبات طبقه اول با T9
	T(9)=ynBox("آیا فوت شده (دو برادر) یا (چهار خواهر) یا (یک برادر و دو خواهر) دارد؟")

	If W=0 AND (T(2)=1 AND T(3)=0 AND T(4)=0 AND T(9)=0) Then
		temp = (2/3) *Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND (T(1)=1 AND T(3)=0 AND T(4)=0 AND T(9)=0) Then
		temp = (1/3) *Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND (T(2)=1 AND T(3)=0 AND T(4)=0 AND T(9)=1) Then
		temp = (5/6) *Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND (T(1)=1 AND T(3)=0 AND T(4)=0 AND T(9)=1) Then
		temp = (1/6) *Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=0 AND T(2)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=0 Then
		temp = Q1/5
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND T(1)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=0 Then
		temp = Q1/5
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=3 AND T(1)=1 AND T(2)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=0 Then
		temp = (3/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=0 AND T(2)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=1 Then
		temp = (5/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=1 AND T(1)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=1 Then
		temp = (4/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
		Return
	Else If W=3 AND T(1)=1 AND T(2)=1 AND T(3)=1 AND Np=0 AND Nd=1 AND T(9)=1 Then
		temp = (15/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"		
		Return
	Else If W=0 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=0 Then
		temp = Q1/5
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=0 Then
		temp = Q1/5
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=5 AND T(1)=1 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=0 Then
		temp = (3/5)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=0 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=1 Then
		temp = (5/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=1 AND T(1)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=1 Then
		temp = (4/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=5 AND T(1)=1 AND T(2)=1 AND T(3)=0 AND T(4)=1 AND Mp=0 AND Md=1 AND T(9)=1 Then
		temp = (15/24)*Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else
		Label2.Text = "حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"	
		Return
	End If
End Sub

Sub calc2
	' محاسبات طبقه دوم
	' مقداردهی متغیرها
	WHP = 0
	If (T(17)=1 AND T(18)=1) _
		OR (T(17)=1 AND T(18)=0 AND a3b>1) _
		OR (T(17)=0 AND T(18)=1 AND a3k>1) _
	    OR (T(17)=1 AND T(18)=0 AND T(26)=1) _
		OR (T(17)=0 AND T(18)=1 AND T(25)=1) _
		OR (T(17)=0 AND T(18)=0 AND T(25)=1 AND T(26)=1) _
		OR (T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=1 AND b3k>1) _
		OR (T(17)=0 AND T(18)=0 AND T(26)=0 AND T(25)=1 AND b3b>1) Then  
		WHP=1
	End If
	
	CMO = 0
	If  isZero(17,20)  AND isZero(25,26) Then
		CMO=1
	End If
	
	QMN = 0
	If  isZero(11,16)  AND isZero(21,24)  Then
		QMN=1
	End If
	
	PQN = 0
	If  T(21)=0 AND T(12)=0 AND  T(13)=0  AND T(14)=0 Then
		PQN=1
	End If
	
	' شروع محاسبات
	If W=9 AND ( T(11)=0 AND isZero(13,26) ) Then
		temp = Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( T(12)=0 AND isZero(13,26) ) Then
		temp = Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(11,18) AND isZero(20,26) ) Then
		temp = Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(11,19) AND isZero(21,26) ) Then
		temp = Q1
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	Else If W=10 AND a1b>0 AND ( isZero(11,12) AND isZero(17,20) AND T(14)=0 AND isZero(25,26) AND T(22)=0 ) Then
		temp = Q1/a1b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=11 AND  a1k>0 AND ( isZero(11,13) AND isZero(17,21) AND T(25)=0 AND T(26)=0 ) Then
		temp = Q1/a1k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=12 AND a2b>0 AND ( isZero(11,14) AND isZero(16,22) AND T(24)=0 AND T(26)=0 ) Then
		temp = Q1/a2b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=13 AND a2k>0 AND ( isZero(11,15) AND isZero(17,23) AND T(25)=0 AND T(26)=0 ) Then
		temp = Q1/a2k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=14 AND a3b>0 AND ( isZero(11,16) AND isZero(18,24) AND T(26)=0 ) Then
		temp = Q1/a3b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=15 AND a3k>0 AND ( isZero(11,17) AND isZero(19,25) ) Then
		temp = Q1/a3k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=16 AND b1b>0 AND ( isZero(11,18) AND isZero(19,20) AND isZero(25,26) AND T(22)=0 ) Then
		temp = Q1/b1b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=17 AND b1k>0 AND ( isZero(11,21) AND isZero(25,26) ) Then
		temp = Q1/b1k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=18 AND b2b>0 AND ( isZero(11,22) AND isZero(24,26) ) Then
		temp = Q1/b2b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=19 AND b2k>0 AND ( isZero(11,23) AND isZero(25,26) ) Then
		temp = Q1/b2k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=20 AND b3b>0 AND ( isZero(11,24) AND T(26)=0 ) Then
		temp = Q1/b3b
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=21 AND b3k>0 AND ( isZero(11,25) ) Then
		temp = Q1/b3k
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(13,18) AND isZero(21,26) AND T(12)=1 AND T(19)=1 AND T(20)=1 ) Then
		temp = (4*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=1 AND T(19)=1 AND T(20)=1 ) Then
		temp = (2*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=1 AND T(12)=1 AND T(20)=1 ) Then
		temp = Q1/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=1 AND T(12)=1 AND T(19)=1 ) Then
		temp = Q1/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(13,26) AND T(12)=1 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,26) AND T(11)=1 ) Then
		temp = Q1/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(11,18) AND isZero(21,26) AND T(20)=1 ) Then
		temp = Q1/2
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(11,18) AND isZero(21,26) AND T(19)=1 ) Then
		temp = Q1/2
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(12,19) AND isZero(21,26) AND T(20)=1 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(12,18) AND isZero(21,26) AND T(19)=1 AND T(20)=0 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(12,18) AND isZero(21,26) AND T(11)=1 AND T(20)=0 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(13,18) AND isZero(21,26) AND T(12)=1 AND T(11)=0 AND T(20)=0 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,19) AND isZero(21,26) AND T(11)=0 AND T(20)=1 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,18) AND isZero(21,26) AND T(19)=1 AND T(11)=0 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(12,19) AND isZero(21,26) AND T(11)=1 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(13,19) AND isZero(21,26) AND T(11)=0 AND T(12)=1 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(13,18) AND isZero(21,26) AND T(12)=0 AND T(19)=1 AND T(20)=1 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=0 AND T(19)=1 AND T(20)=1 ) Then
		temp = (2*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=1 AND T(12)=1 AND T(20)=0 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	Else If W=91 AND ( isZero(13,18) AND isZero(21,26) AND T(19)=0 AND T(11)=1 AND T(12)=1 ) Then
		temp = (1*Q1)/3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	Else If W=81 AND ( isZero(13,18) AND isZero(21,26) AND T(12)=0 AND T(11)=1 AND T(20)=1 ) Then
		temp = (1*Q1)/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(13,18) AND isZero(21,26) AND T(12)=0 AND T(11)=1 AND T(19)=1 ) Then
		temp = (1*Q1)/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=81 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=0 AND T(12)=1 AND T(20)=1 ) Then
		temp = (1*Q1)/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=91 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=0 AND T(19)=1 AND T(12)=1 ) Then
		temp = (1*Q1)/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(13,18) AND isZero(20,26) AND T(19)=1 AND T(12)=1 ) Then
		temp = (4*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,18) AND isZero(20,26) AND T(11)=1 AND T(19)=1 ) Then
		temp = (2*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=8 AND ( isZero(13,18) AND isZero(21,26) AND T(19)=0 AND T(12)=1 AND T(20)=1 ) Then
		temp = (4*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If W=9 AND ( isZero(13,18) AND isZero(21,26) AND T(11)=1 AND T(20)=1 AND T(19)=0 ) Then
		temp = (2*Q1)/9
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If (T(13)=1 AND W=16)OR(T(14)=1 AND W=17)OR(T(15)=1 AND W=18)OR(T(16)=1 AND W=19)OR(T(17)=1 AND W=20)OR(T(18)=1 AND W=21) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return
	
	'--------------- Nasim starts: --------------------
	
	Else If isZero(11,12) AND isZero(19,20) AND W=10 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(14)=1 Then
		temp = (2*Q1)/L3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If isZero(11,12) AND isZero(19,20) AND W=10 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(14)=0 AND T(22)=1 Then
		temp = (2*Q1)/(2*a1b+b1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If isZero(11,12) AND isZero(19,20) AND W=11 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(13)=1 Then
		temp = Q1/L3
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If isZero(11,12) AND isZero(19,20) AND W=11 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(13)=0 AND T(21)=1 Then
		temp = Q1/(2*b1b+a1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If isZero(11,12) AND isZero(19,20) AND W=16 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(13)=0 AND T(14)=1 Then
		temp = (2*Q1)/(2*b1b+a1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If isZero(11,12) AND isZero(19,20) AND W=16 AND T(13)=0 AND T(14)=0 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(22)=1 Then
		temp = (2*Q1)/(2*b1b+b1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If isZero(11,12) AND isZero(19,20) AND W=17 AND T(14)=0 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(13)=1 Then
		temp = (Q1)/(2*a1b+b1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
	Else If isZero(11,12) AND isZero(19,20) AND W=17 AND t(13)=0 AND T(14)=0 AND T(17)=0 AND T(18)=0 AND T(25)=0 AND T(26)=0 AND T(21)=1 Then
		temp = (Q1)/(2*b1b+b1k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
'**********	PAGE 21 PART 2 *********	
	Else If (W=12 OR W=13 OR (T(15)=0 AND W=18) OR (T(16)=0 AND W=19)) AND (T(13)=1 OR T(14)=1 OR (T(13)=0 AND T(21)=1) OR(T(14)=0 AND T(22)=1)) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return
		
'**********PAGE 21 PART 3***********
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=12 AND t(16)=1  Then
		temp = (2*Q1)/(L4)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=12 AND t(16)=0 AND T(24)=1  Then
		temp = (2*Q1)/(2*a2b+b2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		

'*******page 22*******

	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=13 AND T(15)=1  Then
		temp = (Q1)/(L4)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=13 AND T(15)=0 AND T(23)=1  Then
		temp = (Q1)/(2*b1b+a2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=18 AND T(15)=0 AND T(16)=1  Then
		temp = (2*Q1)/(2*b2b+a2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=18 AND T(15)=0 AND T(16)=0 AND T(24)=1  Then
		temp = (2*Q1)/(2*b2b+b2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=19 AND T(15)=1 AND T(16)=0 AND T(24)=1  Then
		temp = (Q1)/(2*a2b+b2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	Else If isZero(13,14) AND isZero(21,22)  AND isZero(17,18)  AND isZero(25,26) AND W=19 AND T(15)=0 AND T(16)=0 AND T(23)=1  Then
		temp = (Q1)/(2*b2b+b2k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
'***************** page 23 ****************
	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=14 AND T(18)=1  Then
		temp = (Q1)/(L5)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=14 AND T(18)=0 AND T(26)=1  Then
		temp = (Q1)/(a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		

	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=15  AND T(17)=1  Then
		temp = (Q1)/(L5)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		

	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=15  AND T(17)=0 AND T(25)=1  Then
		temp = (Q1)/(b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		

	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=20  AND T(17)=0 AND T(18)=1  Then
		temp = (Q1)/(b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	
	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=20  AND T(17)=0 AND T(18)=0 AND T(26)=1  Then
		temp = (Q1)/(b3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=21  AND T(18)=0 AND T(17)=1   Then
		temp = (Q1)/(a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
'**********page 24***************

	Else If isZero(13,16) AND isZero(11,12)  AND isZero(19,20)  AND W=21  AND T(18)=0 AND T(17)=0 AND T(25)=1   Then
		temp = (Q1)/(b3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

'*************page 24 part2**********

	Else If  isZero(11,12)  AND isZero(19,20)  AND W=10  AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1)) AND T(14)=1   Then
		temp = (10*Q1)/(6*L3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If  isZero(11,12)  AND isZero(19,20)  AND W=11  AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1)) AND T(13)=1   Then
		temp = (5*Q1)/(6*L3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=16 AND T(13)=0  AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1)) AND T(14)=1   Then
		temp = (10*Q1)/(6*(2*b3b+a1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=16 AND T(13)=0  AND T(14)=0 AND T(22)=1 AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1))   Then
		temp = (10*Q1)/(6*(2*b1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=10 AND T(14)=0  AND T(22)=1 AND T(22)=1 AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1))   Then
		temp = (10*Q1)/(6*(2*a1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
 '******************page 25**********************
   
   Else If  isZero(11,12)  AND isZero(19,20)  AND W=11 AND T(13)=0  AND T(21)=1 AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1))   Then
		temp = (5*Q1)/(6*(2*b1b+a1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=17 AND T(14)=0  AND T(13)=1 AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1))   Then
		temp = (5*Q1)/(6*(2*a1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=17 AND T(14)=0  AND T(13)=0 AND T(21)=1 AND ((T(17)=1 AND T(18)=0 AND a3b=1) OR (T(18)=1 AND T(17)=0 AND a3k=1))   Then
		temp = (5*Q1)/(6*(2*b1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
'****************page 25 part 2*********************		
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=14 AND(T(13)=1 OR T(14)=1 OR (T(13)=0 AND T(21)=1) OR (T(14)=0 AND T(22)=1)) AND T(18)=0 AND a3b=1   Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=15 AND(T(13)=1 OR T(14)=1 OR (T(13)=0 AND T(21)=1) OR (T(14)=0 AND T(22)=1)) AND T(17)=0 AND a3k=1   Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=20 AND(T(13)=1 OR T(14)=1 OR (T(13)=0 AND T(21)=1) OR (T(14)=0 AND T(22)=1)) AND T(17)=0 AND T(18)=0 AND T(26)=0 AND b3b=1   Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  isZero(11,12)  AND isZero(19,20)  AND W=21 AND(T(13)=1 OR T(14)=1 OR (T(13)=0 AND T(21)=1) OR (T(14)=0 AND T(22)=1)) AND T(17)=0 AND T(18)=0 AND T(25)=0 AND b3k=1   Then
		temp = Q1/6
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
'***********page 26***********************************

	Else If isZero(11,12) AND isZero(19,20) AND  w=10 AND WHP=1 AND T(14)=1   Then
		temp = (4*Q1)/(3*L3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If isZero(11,12) AND isZero(19,20) AND  w=10 AND WHP=1 AND T(14)=0 AND T(22)=1   Then
		temp = (4*Q1)/(3*(2*a1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If isZero(11,12) AND isZero(19,20) AND  w=11 AND WHP=1 AND T(13)=1   Then
		temp = (2*Q1)/(3*L3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

'*****************page 27***********************************
	Else If isZero(11,12) AND isZero(19,20) AND  w=11 AND WHP=1 AND T(13)=0 AND T(21)=1   Then
		temp = (2*Q1)/(3*(2*b1b+a1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If isZero(11,12) AND isZero(19,20) AND  w=16 AND WHP=1 AND T(13)=0 AND T(14)=1   Then
		temp = (4*Q1)/(3*(2*b1b+a1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If isZero(11,12) AND isZero(19,20) AND  w=16 AND WHP=1 AND T(13)=0 AND T(14)=0 AND T(22)=1   Then
		temp = (4*Q1)/(3*(2*b1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If isZero(11,12) AND isZero(19,20) AND  w=17 AND WHP=1 AND T(13)=1 AND T(14)=0 Then
		temp = (2*Q1)/(3*(2*a1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	


	Else If isZero(11,12) AND isZero(19,20) AND  w=17 AND WHP=1 AND T(13)=1 AND T(14)=0 AND T(21)=1 Then
		temp = (2*Q1)/(3*(2*b1b+b1k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

'*************** page 28********************************

	Else If isZero(11,12) AND isZero(19,20) AND  w=14 AND WHP=1 AND T(18)=1 Then
		temp = (Q1)/(3*L5)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If isZero(11,12) AND isZero(19,20) AND  w=14 AND WHP=1 AND T(18)=0 AND T(26)=1 Then
		temp = (Q1)/(3*(a3b+b3k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If isZero(11,12) AND isZero(19,20) AND  w=15 AND WHP=1 AND T(17)=1 Then
		temp = (Q1)/(3*L5)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If isZero(11,12) AND isZero(19,20) AND  w=15 AND WHP=1 AND T(17)=0 AND T(25)=1 Then
		temp = (Q1)/(3*(a3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If isZero(11,12) AND isZero(19,20) AND  w=20 AND WHP=1 AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(3*(a3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(11,12) AND isZero(19,20) AND  w=20 AND WHP=1 AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(3*(a3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(11,12) AND isZero(19,20) AND  w=20 AND WHP=1 AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(3*(a3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If isZero(11,12) AND isZero(19,20) AND  w=20 AND WHP=1 AND T(17)=0 AND T(18)=0 AND T(26)=1 Then
		temp = (Q1)/(3*(b3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
		
'******************page 29**************************************

	Else If isZero(11,12) AND isZero(19,20) AND  w=21 AND WHP=1 AND T(18)=0 AND T(17)=1  Then
		temp = (Q1)/(3*(a3b+b3k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If isZero(11,12) AND isZero(19,20) AND  w=21 AND WHP=1 AND T(17)=0 AND T(18)=0 AND T(25)=1 Then
		temp = (Q1)/(3*(b3k+b3b))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
'*************page 29 part2 **************************************
	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=12 AND WHP=1 AND T(16)=1  Then
		temp = (4*Q1)/(3*L4)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=12 AND WHP=1 AND T(16)=0 AND T(24)=1  Then
		temp = (4*Q1)/(3*(2*a2b+b2k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  isZero(11,14)  AND isZero(19,22)  AND W=13  AND WHP=1 AND T(15)=1  Then
		temp = (2*Q1)/(3*L4)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
'******************page 30************************************

	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=13 AND WHP=1 AND T(15)=0 AND T(23)=1  Then
		temp = (2*Q1)/(3*(2*b2b+a2k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=18 AND WHP=1 AND T(15)=0 AND T(16)=1  Then
		temp = (4*Q1)/(3*(2*b2b+a2k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=18 AND WHP=1 AND T(15)=0 AND T(16)=0 AND T(24)=1  Then
		temp = (4*Q1)/(3*(2*b2b+b2k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return				
		
	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=19 AND WHP=1 AND T(16)=0  AND T(15)=1  Then
		temp = (2*Q1)/(3*(2*a2b+b3k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  isZero(11,14)  AND isZero(19,22)  AND  w=19 AND WHP=1 AND T(15)=0 AND T(16)=0 AND T(23)=1  Then
		temp = (2*Q1)/(3*(2*b2b+b2k))
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
'*************************page 31**********************************************
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=1 AND QMN=1) AND T(17)=1 AND T(18)=1 Then
		temp = (Q1)/(2+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=1 AND QMN=1) AND T(17)=1 AND T(18)=0 Then
		temp = (Q1)/(2+a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=1 AND QMN=1) AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(2+b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=1 AND QMN=1) AND T(17)=0 AND T(18)=0 Then
		temp = (Q1)/(2+b3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=0 AND QMN=1) AND T(17)=1 AND T(18)=1 Then
		temp = (Q1)/(1+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=0 AND QMN=1) AND T(17)=1 AND T(18)=0 Then
		temp = (Q1)/(1+a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=0 AND QMN=1) AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(1+b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  (W=81 AND T(20)=1 AND QMN=1) OR (W=91 AND T(19)=0 AND QMN=1) AND T(17)=0 AND T(18)=0 Then
		temp = (Q1)/(1+b3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
'*****************page 32*********************************
		
	Else If  W=14 AND T(19)=1 AND T(20)=1 AND QMN=1 AND T(18)=1 Then
		temp = (Q1)/(2+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
		
	Else If  W=14 AND T(19)=1 AND T(20)=1 AND QMN=1 AND T(18)=0 Then
		temp = (Q1)/(2+a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=14 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1))AND QMN=1 AND T(18)=1 Then
		temp = (Q1)/(1+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=14 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1))AND QMN=1 AND T(18)=0 Then
		temp = (Q1)/(1+a3b+b3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=15 AND T(19)=1 AND T(20)=1  AND QMN=1 AND T(17)=1 Then
		temp = (Q1)/(2+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If  W=15 AND T(19)=1 AND T(20)=1  AND QMN=1 AND T(17)=0 Then
		temp = (Q1)/(2+b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=15 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1))AND QMN=1 AND T(17)=1 Then
		temp = (Q1)/(1+a3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
	Else If  W=15 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1))AND QMN=1 AND T(17)=0 Then
		temp = (Q1)/(1+b3b+a3k)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return				
		
		
'******************* page 33 ******************************************************

	Else If  W=20 AND T(19)=1 AND T(20)=1 AND QMN=1 AND T(17)=0 AND T(18)=1 Then
		temp = (Q1)/(2+a3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If  W=20 AND T(19)=1 AND T(20)=1  AND QMN=1 AND T(17)=0 AND T(18)=0 Then
		temp = (Q1)/(1+a3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			

	Else If  W=20 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1)) AND QMN=1 AND T(18)=1 Then
		temp = (Q1)/(1+b3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			

	Else If  W=20 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1)) AND QMN=1 AND T(18)=0 Then
		temp = (Q1)/(2+b3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			

	Else If  W=21 AND T(19)=1 AND T(20)=1 AND T(18)=0 AND  QMN=1 AND T(17)=1 Then
		temp = (Q1)/(2+b3k+a3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
	Else If  W=21 AND T(19)=1 AND T(20)=1 AND T(18)=0 AND  QMN=1 AND T(17)=0 Then
		temp = (Q1)/(2+b3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

	Else If  W=21 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1)) AND QMN=1 AND T(17)=1 Then
		temp = (Q1)/(1+b3k+a3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		

	Else If  W=21 AND ((T(19)=1 AND T(20)=0 ) OR (T(19)=0 AND T(20)=1)) AND QMN=1 AND T(17)=0 Then
		temp = (Q1)/(1+b3k+b3b)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
'*****************************page 34**************************************************
		
	Else If  (W=12 OR W=13 OR (T(15)=0 AND W=18) OR (W=19 AND T(16)=0)) AND _
	         (T(13)=1 OR T(14)=1 OR (T(13)=1 AND T(21)=1) OR (T(22)=1 AND T(14)=0))  Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return	
		
	Else If  W=8 AND T(12)=1 AND CMO=1 AND T(13)=1 AND T(14)=1  Then
		temp = (2*Q1)/(L3+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=8 AND T(12)=1 AND CMO=1 AND T(13)=1 AND T(14)=0  Then
		temp = (2*Q1)/(2*a1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	
	Else If  W=8 AND T(12)=1 AND CMO=1 AND T(13)=0 AND T(14)=1  Then
		temp = (2*Q1)/(2*b1b+a1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	
	Else If  W=8 AND T(12)=1 AND CMO=1 AND T(13)=0 AND T(14)=0  Then
		temp = (2*Q1)/(2*b1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
	
	Else If  W=8 AND T(12)=0 AND CMO=1 AND T(13)=1 AND T(14)=1  Then
		temp = (2*Q1)/(L3+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=8 AND T(12)=0 AND CMO=1 AND T(13)=1 AND T(14)=0  Then
		temp = (2*Q1)/(2*a1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=8 AND T(12)=0 AND CMO=1 AND T(13)=0 AND T(14)=1  Then
		temp = (2*Q1)/(2*b1b+a1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=8 AND T(12)=0 AND CMO=1 AND T(13)=0 AND T(14)=0  Then
		temp = (2*Q1)/(2*b1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
'***********************page 35*****************************************************

	Else If  W=9 AND T(11)=1 AND CMO=1 AND T(13)=1 AND T(14)=1  Then
		temp = (Q1)/(L3+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=1 AND CMO=1 AND T(13)=1 AND T(14)=0  Then
		temp = (Q1)/(2*a1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=1 AND CMO=1 AND T(13)=0 AND T(14)=1  Then
		temp = (Q1)/(2*b1b+a1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=1 AND CMO=1 AND T(13)=0 AND T(14)=0  Then
		temp = (Q1)/(2*b1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=0 AND CMO=1 AND T(13)=1 AND T(14)=1  Then
		temp = (Q1)/(L3+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=0 AND CMO=1 AND T(13)=1 AND T(14)=0  Then
		temp = (Q1)/(2*a1b+b1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=0 AND CMO=1 AND T(13)=0 AND T(14)=1  Then
		temp = (Q1)/(2*b1b+a1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9 AND T(11)=0 AND CMO=1 AND T(13)=0 AND T(14)=0  Then
		temp = (Q1)/(2*b1b+b1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=10 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(14)=1  Then
		temp = (2*Q1)/(L3+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=10 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(14)=0  Then
		temp = (2*Q1)/(2*a1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=10 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(14)=1  Then
		temp = (2*Q1)/(L3+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return



	Else If  W=10 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(14)=0  Then
		temp = (2*Q1)/(2*a1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
'************************page 36******************************************

	Else If  W=10 AND T(11)=0 AND T(12)=1 AND CMO=1 AND  T(14)=1  Then
		temp = (2*Q1)/(L3+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=10 AND T(11)=0 AND T(12)=1 AND CMO=1 AND  T(14)=0  Then
		temp = (2*Q1)/(2*a1b+b1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return



	Else If  W=11 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=1  Then
		temp = (Q1)/(L3+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=11 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=0  Then
		temp = (Q1)/(2*b1b+a1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return



	Else If  W=11 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(13)=1  Then
		temp = (Q1)/(L3+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=11 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=0  Then
		temp = (Q1)/(2*b1b+a1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=16 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=0  AND T(14)=1 Then
		temp = (2*Q1)/(2*b1b+a1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=16 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=0  AND T(14)=0 Then
		temp = (2*Q1)/(2*b1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=16 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(13)=0  AND T(14)=1 Then
		temp = (2*Q1)/(2*b1b+a1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=16 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(13)=0  AND T(14)=0 Then
		temp = (2*Q1)/(2*b1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

'***********************page 37**************************************************

	Else If  W=16 AND T(11)=0 AND T(12)=1 AND CMO=1 AND  T(13)=0  AND T(14)=1 Then
		temp = (2*Q1)/(2*b1b+a1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=1  AND T(14)=0 Then
		temp = (Q1)/(2*a1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=1 AND T(12)=1 AND CMO=1 AND  T(13)=0  AND T(14)=0 Then
		temp = (Q1)/(2*b1b+b1k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(13)=1  AND T(14)=0 Then
		temp = (Q1)/(2*a1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=1 AND T(12)=0 AND CMO=1 AND  T(13)=0  AND T(14)=0 Then
		temp = (Q1)/(2*b1b+b1k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=0 AND T(12)=1 AND CMO=1 AND  T(13)=1  AND T(14)=0 Then
		temp = (Q1)/(2*a1b+b1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return


	Else If  W=17 AND T(11)=0 AND T(12)=1 AND CMO=1 AND  T(13)=0  AND T(14)=0 Then
		temp = (Q1)/(2*b1b+b1k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If  W=8  AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=1 Then
		temp = (2*Q1)/(2*a2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

		
	Else If  W=8  AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=0 Then
		temp = (2*Q1)/(2*a2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	

		
	Else If  W=8  AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=1 Then
		temp = (2*Q1)/(2*b2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

		
	Else If  W=8  AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=0 Then
		temp = (2*Q1)/(2*b2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
'******************page 38********************************************************
		
	Else If  W=8  AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=1 Then
		temp = (2*Q1)/(2*a2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	
	Else If  W=8  AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=0 Then
		temp = (2*Q1)/(2*a2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=8  AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=1 Then
		temp = (2*Q1)/(2*b2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=8  AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=0 Then
		temp = (2*Q1)/(2*b2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return

	Else If  W=9  AND T(11)=1 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=1 Then
		temp = (Q1)/(2*a2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return
		
	Else If  W=9  AND T(11)=1 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=0 Then
		temp = (Q1)/(2*a2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  W=9  AND T(11)=1 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=1 Then
		temp = (Q1)/(2*b2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=9  AND T(11)=1 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=0 Then
		temp = (Q1)/(2*b2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
		
	Else If  W=9  AND T(11)=0 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=1 Then
		temp = (Q1)/(2*a2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=9  AND T(11)=0 AND CMO=1 AND PQN=1 AND T(15)=1  AND T(16)=0 Then
		temp = (Q1)/(2*a2b+b2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=9  AND T(11)=0 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=1 Then
		temp = (Q1)/(2*b2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=9  AND T(11)=0 AND CMO=1 AND PQN=1 AND T(15)=0  AND T(16)=0 Then
		temp = (Q1)/(2*b2b+b2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
'*****************page 39***********************************************************
		
	Else If  W=12  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(16)=1  Then
		temp = (2*Q1)/(2*a2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=12  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(16)=0  Then
		temp = (2*Q1)/(2*a2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=12  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(16)=1  Then
		temp = (2*Q1)/(2*a2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
	Else If  W=12  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(16)=0  Then
		temp = (2*Q1)/(2*a2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
	Else If  W=12  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(16)=1  Then
		temp = (2*Q1)/(2*a2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=12  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(16)=0  Then
		temp = (2*Q1)/(2*a2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=13  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1  Then
		temp = (Q1)/(2*a2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=13  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0  Then
		temp = (Q1)/(2*b2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
'****************page 40*********************************************************

		
	Else If  W=13  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=1  Then
		temp = (Q1)/(2*a2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
			
	Else If  W=13  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0  Then
		temp = (Q1)/(2*b2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
		
			
	Else If  W=13  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1  Then
		temp = (Q1)/(2*a2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
			
	Else If  W=13  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0  Then
		temp = (Q1)/(2*b2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
			
	Else If  W=18  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=1  Then
		temp = (2*Q1)/(2*b2b+a2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
			
	Else If  W=18  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (2*Q1)/(2*b2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
			
	Else If  W=18  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=1  Then
		temp = (2*Q1)/(2*b2b+a2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
			
	Else If  W=18  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (2*Q1)/(2*b2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
'*********************8page 41*****************************		
			
	Else If  W=18  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=1  Then
		temp = (2*Q1)/(2*b2b+a2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
			
	Else If  W=18  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (2*Q1)/(2*b2b+b2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
			
	Else If  W=19  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1 AND T(16)=0  Then
		temp = (Q1)/(2*a2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return				
			
	Else If  W=19  AND T(11)=1 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (Q1)/(2*b2b+b2k+3)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
		
			
	Else If  W=19  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=1 AND T(16)=0  Then
		temp = (Q1)/(2*a2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
				
	Else If  W=19  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (Q1)/(2*b2b+b2k+2)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return	
				
	Else If  W=19  AND T(11)=0 AND T(12)=1 AND CMO=1 AND PQN=1 AND T(15)=1 AND T(16)=0  Then
		temp = (Q1)/(2*a2b+b2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return			
		
	Else If  W=19  AND T(11)=1 AND T(12)=0 AND CMO=1 AND PQN=1 AND T(15)=0 AND T(16)=0  Then
		temp = (Q1)/(2*b2b+b2k+1)
		Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
		Return		
		
'******************page 42,43,44 **************************************
	Else
		If isOne(11,16) OR isOne(21,24) Then
			WNP=1
		End If
		If T(19)=0 AND T(20)=0 AND WNP=1 AND ( _
			(w=14 AND T(18)=0 AND T(26)=0 AND a3b=1)OR _
			(w=20 AND T(17)=0 AND T(18)=0 AND T(26)=0 AND b3b=1)OR _
			(w=15 AND T(17)=0 AND T(25)=0 AND a3k=1)OR _
			(w=21 AND T(18)=0 AND T(17)=0 AND T(25)=0 AND b3k=1) _
			) Then
			temp = Q1/ 6
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
		If (T(17)=1 AND T(18)=0 AND T(19)=0 AND T(20)=0 AND T(26)=0) OR _
			(T(18)=1 AND T(17)=0 AND T(25)=0 AND T(19)=0 AND T(20)=0) OR _
			(T(25)=1 AND T(17)=0 AND T(18)=0 AND T(19)=0 AND T(20)=0 AND T(26)=0) OR _
			(T(26)=1 AND T(17)=0 AND T(18)=0 AND T(19)=0 AND T(20)=0 AND T(25)=0) Then
			CLQ=1
		End If
		
		If T(11)=1 Then sum=sum+2
		If T(12)=1 Then sum=sum+1
		If T(13)=1 Then sum=sum+(2*a1b)
		If T(14)=1 Then sum=sum+a1k
		If (T(15)=1 AND T(13)=0 AND T(14)=0 AND T(20)=0 AND T(21)=0) Then sum=sum+(2*a2b)
		If (T(16)=1 AND T(13)=0 AND T(14)=0 AND T(21)=0 AND T(22)=0) Then sum=sum+(a2k)
		If (T(21)=1 AND T(13)=0)  Then sum=sum+(2*b1b)
		If (T(22)=1 AND T(14)=0 ) Then sum=sum+(b1k)
		If (T(23)=1 AND T(13)=0 AND T(14)=0 AND T(15)=0 AND T(21)=0 AND T(22)=0) Then sum=sum+(2*b2b)
		If (T(24)=1 AND T(13)=0 AND T(14)=0 AND T(16)=0 AND T(21)=0 AND T(22)=0) Then sum=sum+(2*b2k)
		
		If (WNP=1 AND CLQ=1) AND (w=9 OR w=11 OR (T(14)=0 AND w=17) OR (w=13 AND T(13)=0 AND T(14)=0 AND T(21)=0 AND T(22)=0) OR (w=19 AND T(13)=0 AND T(14)=0 AND T(21)=0 AND T(22)=0 AND T(16)=0) ) Then
			temp = (5*Q1)/(6*sum)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
		If T(19)=1 OR T(20)=1 OR (a3b>1 AND T(17)=1) OR (T(17)=1 AND T(19)=1) OR (T(17)=1 AND T(20)=1) OR _
			(T(17)=0 AND T(25)=1 AND b3b>1) OR (T(17)=0 AND T(19)=1 AND T(25)=1) OR(T(17)=0 AND T(20)=1 AND T(25)=1) OR _
			(T(17)=1 AND T(18)=1) OR (T(18)=1 AND T(25)=1 AND T(17)=0) OR (T(25)=1 AND T(26)=1 AND T(17)=0 AND T(18)=0) OR _
			(a3k>1 AND T(18)=1) OR (T(18)=1 AND T(19)=1) OR (T(18)=1 AND T(20)=1) OR (T(18)=0 AND T(26)=1 AND b3k>1) OR _
			(T(19)=1 AND T(26)=1 AND T(18)=0) OR (T(26)=1 AND T(20)=1 AND T(18)=0) OR (T(17)=1 AND T(26)=1 AND T(18)=0) Then
			DON=1
		End If
		If T(19)=1 Then xsum=xsum+1
		If T(20)=1 Then xsum=xsum+1
		If T(17)=1 Then xsum=xsum+(a3b)
		If T(18)=1 Then xsum=xsum+(a3k)
		If T(25)=1 AND T(17)=0 Then xsum=xsum+(b3b)
		If T(26)=1 AND T(18)=0 Then xsum=xsum+(b3k)
		
		If (WNP=1 AND DON=1) AND (w=81 OR w=91 OR w=14 OR w=15 OR(w=20 AND T(17)=0) OR(w=21 AND T(18)=0) ) Then
			temp = Q1/(3*xsum)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
		If (WNP=1 AND DON=1) AND (w=8 OR w=10 OR(w=16 AND T(13)=0) OR (w=12 AND isZero(13,14) AND isZero(21,22)) OR (w=18 AND isZero(13,15) AND isZero(21,22)) ) Then
			temp = (4*Q1)/(3*xsum)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If	
		If (WNP=1 AND DON=1) AND (w=9 OR w=11 OR(w=17 AND T(14)=0) OR (w=13 AND isZero(13,14) AND isZero(21,22)) OR (w=19 AND T(16)=0 AND isZero(13,14) AND isZero(21,22)) ) Then
			temp = (2*Q1)/(3*xsum)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If

	' in case nothimg comes true!
	Label2.Text = "حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"	
	Return	
End Sub

Sub calc3
	If isZero(34,39) AND isZero(46,51) Then M2=1
	If isZero(28,33) AND isZero(40,45) Then M1=1
	
	If (w=24 OR w=25 OR (T(30)=0 AND w=36) OR (T(31)=0 AND w=37) ) AND _
		(T(28)=1 OR T(29)=1 OR T(40)=1 OR T(41)=1) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return
	End If
	
	If ( w=30 OR w=31 OR ( T(36)=0 AND w=42 ) OR (T(37)=0 AND w=43) ) AND _
		( T(34)=1 OR T(35)=1 OR T(46)=1 OR T(47)=1 ) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return
	End If
	
	If T(28)=1 Then WSUM=WSUM+ 2*c22
	If T(29)=1 Then WSUM=WSUM+ c23
	If T(28)=1 AND T(40)=1 Then WSUM=WSUM+2*c34
	If T(29)=0 AND T(41)=1 Then WSUM=WSUM+c35
	If T(28)=1 AND T(40)=1 Then WSUM=WSUM+2*c34
	If ( ( isZero(28,29) AND isZero(40,41) ) AND T(30)=1 ) Then WPSUM=WPSUM+ 2*c24
	If ( ( isZero(28,29) AND isZero(40,41) ) AND T(30)=0 AND T(42)=1 ) Then WPSUM=WPSUM+ 2*c36
	If ( ( isZero(28,29) AND isZero(40,41) ) AND T(31)=1 ) Then WPSUM=WPSUM+ c25
	If ( ( isZero(28,29) AND isZero(40,41) ) AND T(31)=0 AND T(43)=1 ) Then WPSUM=WPSUM+ c37
		'---- page 53 -------
	If ( isZero(28,29) AND isZero(40,41) )  Then PP=2
	If ( isZero(32,33) AND isZero(44,45) ) AND ( POW=1 AND T(27)=1 )AND _
		(w=22 OR (w=34 AND t(28)=0) OR (w=24 AND PP=2) OR (w=36 AND t(42)=1 AND t(30)=0 AND PP=2)) Then
		If M2<>1 Then
		 	temp = (4*Q1)/(3*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
			Return
		Else If 	M2=1 Then
			temp = (2*Q1)/(WSUM+WPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	
	
	If (T(27)=1 AND POW=1) AND (isZero(32,33) AND isZero(44,45)) AND _
	   ( W=23 OR (T(29)=0 AND W=35) OR (W=25 AND PP=2) OR _
	   ( W=37 AND PP=2 AND T(31)=0 AND T(43)=1)) Then
		If M2=1 Then
		  	temp = (Q1)/(WSUM+WPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If 	M2<>1 Then
			temp = (2*Q1)/(3*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If   
	
	
	If T(32)=1 Then MSUM=MSUM+c26
	If T(33)=1 Then MSUM=MSUM+c27
	If (T(32)=0 AND T(44)=1)Then MSUM=MSUM+c38
	If (T(33)=0 AND T(45)=1) Then MSUM=MSUM+c39
	
	'************** PAGE 54 ************************
	If (isZero(28,43) AND (T(27)=1 AND POW=1) ) AND (W=26 OR W=27 OR (W=38 AND T(32)=0) OR (W=39 AND T(33)=0)) Then
		If M2=1 Then
		  	temp = (Q1)/(MSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"
			Return
		Else If M2<>1 Then
		temp = (2*Q1)/(3*MSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If 	
	
	If ((T(32)=1 AND c26=1 AND T(33)=0 AND T(45)=0) OR _
	   (T(32)=0 AND T(44)=1 AND c38=1 AND T(33)=0 AND T(45)=0) _
	  OR (T(33)=1 AND c27=1 AND T(32)=0 AND T(44)=0) _
	  OR (T(33)=0 AND T(45)=1 AND c39=1 AND T(32)=0 AND T(44)=0) ) AND _
	  (T(27)=1 AND POW=1) AND ( T(28)<>0 OR T(29)<>0 OR T(30)<>0 OR (31)<>0 OR T(40)<>0 OR T(41)<>0 OR T(42)<>0 OR T(43)<>0) Then
	  CK=1
	End If 
	  
	If CK=1 AND ( w=26 OR w=27 OR (t(32)=0 AND w=38) OR (t(33)=0 AND w=39)) Then  
		If M2=1 Then
		  	temp = (Q1)/6
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If M2<>1 Then
			temp = Q1/9
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	
	'------------- page 55 -------------
		  
	If CK=1 AND (w=22 OR (t(28)=0 AND w=34)  OR _
		(w=34 AND isZero(28,29) AND isZero(40,41) ) OR (isZero(28,30)AND isZero(40,41) AND w=36) ) Then  
		If M2=1 Then
		  	temp = (5*Q1)/(3*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If 	M2<>1 Then
			temp = (10*Q1)/(9*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	
	If CK=1 AND (w=23 OR (t(29)=0 AND w=35)OR (isZero(28,29) AND isZero(40,41)AND w=25 )) OR (isZero(28,29) AND isZero(40,41)AND w=37 AND t(31)=0)Then
		If M2=1 Then
		  	temp = (5*Q1)/ (6*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If 	M2<>1 Then
		   temp = (5*Q1)/(9*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	'------------ page 56 -----------
	If ( (T(32)=1 AND c26<1) OR (T(32)=0 AND T(44)=1 AND c38>1) OR _
		(c27>1 AND T(33)=1) OR (T(33)=0 AND T(45)=1 AND c39>1) OR _
		(T(32)=1 AND T(33)=1) OR (T(32)=1 AND T(33)=0 AND T(45)=1) OR _
		(T(32)=0 AND T(44)=1 AND T(33)=1) OR (isZero(32,33) AND T(44)=1 AND T(45)=1 ) ) AND _
		(T(27)=1 AND POW=1) AND (T(28)<>0 OR T(29)<>0 OR T(30)<>0 OR (31)<>0 OR T(40)<>0 OR T(41)<>0 OR T(42)<>0 OR T(43)<>0) Then
		CK=2
	End If
	If CK=2 AND ( w=26 OR w=27 OR (w=38 AND T(32)=0) OR (w=39 AND T(33)=0) ) Then
		If M2=1 Then
		  	temp = (Q1)/ (3*MSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If 	M2<>1 Then
			temp = (2*Q1)/(9*MSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If		
	End If
	If CK=2 AND ( w=22 OR (w=34 AND T(28)=0) OR (w=24 AND isZero(28,29) AND isZero(40,41)) OR (w=36 AND T(30)=0 AND isZero(28,29) AND isZero(40,41)) ) Then
		If M2=1 Then
		  	temp = (4*Q1)/ (3*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If 	M2<>1 Then
			temp = (8*Q1)/(9*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If		
	End If	
	'--------- page 57 ---------
	If CK=2 AND ( w=23 OR (w=35 AND T(29)=0) OR (w=25 AND isZero(28,29) AND isZero(40,41)) OR (w=37 AND T(31)=0 AND isZero(28,29) AND isZero(40,41)) ) Then
		If M2=1 Then
		  	temp = (2*Q1)/ (3*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If  M2<>1 Then
		  	temp = (4*Q1)/ (9*(WSUM+WPSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If		
	End If		
	If (w=30 OR w=31 OR (w=42 AND T(36)=0) OR(w=43 AND T(37)=0) ) AND _
		(T(34)=1 OR T(35)=1 OR T(46)=1 OR T(47)=1 ) Then
		Label2.Text = "متاسفانه ارثی به شما تعلق نمیگیرد!"
		Return		
	End If
	If T(34)=1 Then KSUM=KSUM+c28
	If T(35)=1 Then KSUM=KSUM+c29
	If (T(34)=0 AND T(46)=1) Then KSUM=KSUM+c40
	If (T(35)=0 AND T(47)=1) Then KSUM=KSUM+c41
	If (isZero(34,35) AND isZero(46,47) AND T(36)=1 ) Then KSUM=KSUM+c30
	If (isZero(34,35) AND isZero(46,47) AND T(37)=1 ) Then KSUM=KSUM+c31
	If (isZero(34,35) AND isZero(46,47) AND T(36)=0 AND T(48)=1 ) Then KSUM=KSUM+c42
	If (isZero(34,35) AND isZero(46,47) AND T(37)=0 AND T(49)=1 ) Then KSUM=KSUM+c43
	'--------- page 58 ------------
	If (T(27)=1 AND POW=1 AND isZero(38,39) AND isZero(50,51) ) AND _
		( w=28 OR (w=40 AND T(46)=1) OR w=29 OR (w=41 AND T(47)=1) OR _
		( (isZero(34,35) AND isZero(46,47)) AND (w=30 OR w=31 OR (w=42 AND T(36)=0) OR (w=43 AND T(37)=0) ) )) Then
		If M1=1 Then
		  	temp = (Q1)/ (KSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If  M1<>1 Then
		  	temp = (Q1)/ (3*(KSUM))
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If		
	End If
	If T(38)=1 Then KPSUM=KPSUM+c32
	If T(39)=1 Then KPSUM=KPSUM+c33
	If (T(38)=0 AND T(50)=1) Then KPSUM=KPSUM+c44
	If (T(39)=0 AND T(51)=1) Then KPSUM=KPSUM+c45		

	If (POW=1 AND T(27)=1) AND (isZero(34,37) AND isZero(46,49) ) AND _
		( w=32 OR w=33 OR (w=44 AND T(38)=0) OR (T(39)=0 AND w=45 ) ) Then
		If M1=1 Then
		  	temp = (Q1)/ (KPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If  M1<>1 Then
		  	temp = (Q1)/ (3*KPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If		
	End If
	'--------- page 59 ----------
	If ( (T(38)=1 AND c32=1 AND T(39)=0 AND T(51)=0) OR _
		( T(38)=0 AND T(50)=1 AND c44=1 AND T(39)=0 AND T(51)=0 ) OR _
		(T(38)=0 AND T(50)=0 AND T(39)=1 AND c33=1 ) OR _
		( T(38)=0 AND T(50)=0 AND T(39)=0 AND T(51)=1 AND c45=1  ) ) AND _
		(POW=1 AND T(27)=1) AND (isOne(34,37) OR isOne(46,49)) Then
		CK=3
	End If

	If CK=3 AND ( w=32 OR w=33 OR (t(38)=0 AND w=44) OR (t(39)=0 AND w=45)) Then  
		If M1=1 Then
		  	temp = (Q1)/6
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If M1<>1 Then
			temp = Q1/18
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If	

	If CK=3 AND ( ( w=28 OR (w=40 AND T(34)=0) OR w=29 OR (w=41 AND T(35)=0) ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=30 ) OR _
		( isZero(34,36) AND isZero(46,47) AND w=42 ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=31 ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=43 ) ) Then  
		If M1=1 Then
		  	temp = (5*Q1)/ (6*KSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If M1<>1 Then
			temp = (5*Q1)/ (18*KSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	'-------------  page 60 ------------
	If ( (T(38)=1 AND c32>1) OR (T(39)=1 AND c33>1) OR _
		(T(38)=0 AND T(50)=1 AND c44>1) OR (T(39)=0 AND T(51)=1 AND c45>1) OR _
		(T(38)=1 AND T(39)=1) OR (T(38)=0 AND T(50)=1 AND T(39)=1) OR _ 
		(T(38)=1 AND T(39)=0 AND T(51)=1) OR (T(38)=0 AND T(39)=0 AND T(50)=1 AND T(51)=1) ) _ 
		AND (T(27)=1 AND POW=1) AND (isOne(34,37) OR isOne(46,49)) Then
		CK=4
	End If
	
	If CK=4 AND ( w=32 OR w=33 OR (t(38)=0 AND w=44) OR (t(39)=0 AND w=45)) Then  
		If M1=1 Then
		  	temp = Q1/ (3*KPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If M1<>1 Then
			temp = Q1/ (9*KPSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If	
	'-- page 61 ------
	If CK=4 AND ( ( w=28 OR (w=40 AND T(34)=0) OR w=29 OR (w=41 AND T(35)=0) ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=30 ) OR _
		( isZero(34,36) AND isZero(46,47) AND w=42 ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=31 ) OR _
		( isZero(34,35) AND isZero(46,47) AND w=43 AND T(37)=0 ) ) Then  
		If M1=1 Then
		  	temp = (2*Q1)/ (3*KSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		Else If M1<>1 Then
			temp = (2*Q1)/ (9*KSUM)
			Label2.Text = NumberFormat(temp,0,4) & " درصد ارث به شما تعلق میگیرد"	
			Return
		End If
	End If
	
	' in case nothimg comes true!
	Label2.Text = "حالت استثنا! شما می توانید با توسعه دهنده نرم افزار جهت بررسی این حالت تماس بگیرید"	
	Return
End Sub

Sub isZero(a As Int,b As Int) As Boolean ' 0 and 0 and 0 ...
	For i=a To b
		If T(i) <> 0 Then
			Return False
		End If
	Next
	Return True
End Sub

Sub isOne(a As Int,b As Int) As Boolean ' 1 or 1 or 1 ...
	For i=a To b
		If T(i) = 1 Then
			Return True
		End If
	Next
	Return False
End Sub
