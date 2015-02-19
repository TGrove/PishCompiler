	CONST a 5
	CONST bbb 6
	CONST c 10
	CONST d 20
	CONST aa 5.5
	CONST bb 7.7
	CONST cc 8.8
COUNTDOWN: nop
	JUMP WRITEINT
	#BEGIN ASSIGN STMT
	T59(20) := T60(24)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T64(40) := T62(32) SUBTRACT T63(36)
	T61(28) := T64(40)
	#END ASSIGN STMT
	#BEIGN IF STMT
		T67(52) := T65(44) LE T66(48)
		if T67(52) := 0 goto L0
		#BEGIN ASSIGN STMT
		T68(56) := T69(60)
		#END ASSIGN STMT
		goto L1
		L0: nop
		#BEGIN ASSIGN STMT
		JUMP COUNTDOWN
		T70(64) := T72(72)
		#END ASSIGN STMT
		L1: nop
	#END IF STMT
COUNTUP: nop
	JUMP WRITEINT
	#BEGIN ASSIGN STMT
	T76(20) := T77(24)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T81(40) := T79(32) ADD T80(36)
	T78(28) := T81(40)
	#END ASSIGN STMT
	#BEIGN IF STMT
		T84(52) := T82(44) GE T83(48)
		if T84(52) := 0 goto L2
		#BEGIN ASSIGN STMT
		T85(56) := T86(60)
		#END ASSIGN STMT
		goto L3
		L2: nop
		#BEGIN ASSIGN STMT
		JUMP COUNTUP
		T87(64) := T89(72)
		#END ASSIGN STMT
		L3: nop
	#END IF STMT
	#BEGIN ASSIGN STMT
	T90(4) := T91(8)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T92(12) := T93(16)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T94(20) := T95(24)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T96(28) := T97(32)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T98(36) := T99(40)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T100(44) := T101(48)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	T102(52) := T103(56)
	#END ASSIGN STMT
	#BEGIN WHILE LOOP
	L6: nop
		T106(68) := T104(60) LT T105(64)
		if T106(68) := 0 goto L7
		#BEGIN ASSIGN STMT
		T107(72) := T108(76)
		#END ASSIGN STMT
		#BEGIN WHILE LOOP
		L4: nop
			T111(88) := T109(80) LT T110(84)
			if T111(88) := 0 goto L5
			#BEGIN ASSIGN STMT
			T116(108) := T114(100) ADD T115(104)
			T117(112) := T113(96) ADD T116(108)
			T112(92) := T117(112)
			#END ASSIGN STMT
			#BEGIN ASSIGN STMT
			T121(128) := T119(120) ADD T120(124)
			T118(116) := T121(128)
			#END ASSIGN STMT
			goto L4
		L5: nop
		#END WHILE LOOP
		#BEGIN ASSIGN STMT
		T125(144) := T123(136) ADD T124(140)
		T122(132) := T125(144)
		#END ASSIGN STMT
		goto L6
	L7: nop
	#END WHILE LOOP
	JUMP WRITEINT
	#BEGIN ASSIGN STMT
	T128(156) := T129(160)
	#END ASSIGN STMT
	#BEGINING FOR LOOP
	L10: nop
		T141(172) := &JJ
		*T141(172) := T130(164)
		if T141(172) := T131(168) goto L11
		#BEGINING FOR LOOP
		L8: nop
			T139(184) := &ee
			*T139(184) := T132(176)
			if T139(184) := T133(180) goto L9
			#BEGIN ASSIGN STMT
			T137(200) := T135(192) ADD T136(196)
			T134(188) := T137(200)
			#END ASSIGN STMT
			*T139(184) := *T139(184) + 1
			goto L8
		L9: nop
		#ENDING FOR LOOP
		*T141(172) := *T141(172) + 1
		goto L10
	L11: nop
	#ENDING FOR LOOP
	JUMP WRITEINT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T144(216) * 4
	#moving the array point to the position of the record
	T146(220) = T145(212) + RG#(0)
	T146(220) := T147(224)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T148(232) * 4
	#moving the array point to the position of the record
	T150(236) = T149(228) + RG#(0)
	T150(236) := T151(240)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T152(248) * 4
	#moving the array point to the position of the record
	T154(252) = T153(244) + RG#(0)
	T154(252) := T155(256)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T156(264) * 4
	#moving the array point to the position of the record
	T158(268) = T157(260) + RG#(0)
	T158(268) := T159(272)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T160(280) * 4
	#moving the array point to the position of the record
	T162(284) = T161(276) + RG#(0)
	T162(284) := T163(288)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T164(296) * 4
	#moving the array point to the position of the record
	T166(300) = T165(292) + RG#(0)
	T166(300) := T167(304)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T168(312) * 4
	#moving the array point to the position of the record
	T170(316) = T169(308) + RG#(0)
	T170(316) := T171(320)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T172(328) * 4
	#moving the array point to the position of the record
	T174(332) = T173(324) + RG#(0)
	T174(332) := T175(336)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T176(344) * 4
	#moving the array point to the position of the record
	T178(348) = T177(340) + RG#(0)
	T178(348) := T179(352)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T180(360) * 4
	#moving the array point to the position of the record
	T182(364) = T181(356) + RG#(0)
	T182(364) := T183(368)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T184(376) * 4
	#moving the array point to the position of the record
	T186(380) = T185(372) + RG#(0)
	T186(380) := T187(384)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T188(392) * 4
	#moving the array point to the position of the record
	T190(396) = T189(388) + RG#(0)
	#Calculating off set of the record in the array
	RG#(0) = T191(404) * 4
	#moving the array point to the position of the record
	T193(408) = T192(400) + RG#(0)
	T195(416) := T193(408) MUL T194(412)
	T190(396) := T195(416)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T196(424) * 4
	#moving the array point to the position of the record
	T198(428) = T197(420) + RG#(0)
	T198(428) := T199(432)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T200(440) * 4
	#moving the array point to the position of the record
	T202(444) = T201(436) + RG#(0)
	T202(444) := T203(448)
	#END ASSIGN STMT
	#BEGIN ASSIGN STMT
	#Calculating off set of the record in the array
	RG#(0) = T204(456) * 4
	#moving the array point to the position of the record
	T206(460) = T205(452) + RG#(0)
	#Calculating off set of the record in the array
	RG#(0) = T207(468) * 4
	#moving the array point to the position of the record
	T209(472) = T208(464) + RG#(0)
	T211(480) := T209(472) MUL T210(476)
	T206(460) := T211(480)
	#END ASSIGN STMT
	JUMP WRITEINT
	#Calculating off set of the record in the array
	RG#(0) = T212(488) * 4
	#moving the array point to the position of the record
	T214(492) = T213(484) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T216(504) * 4
	#moving the array point to the position of the record
	T218(508) = T217(500) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T220(520) * 4
	#moving the array point to the position of the record
	T222(524) = T221(516) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T224(536) * 4
	#moving the array point to the position of the record
	T226(540) = T225(532) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T228(552) * 4
	#moving the array point to the position of the record
	T230(556) = T229(548) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T232(568) * 4
	#moving the array point to the position of the record
	T234(572) = T233(564) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T236(584) * 4
	#moving the array point to the position of the record
	T238(588) = T237(580) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T240(600) * 4
	#moving the array point to the position of the record
	T242(604) = T241(596) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T244(616) * 4
	#moving the array point to the position of the record
	T246(620) = T245(612) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T248(632) * 4
	#moving the array point to the position of the record
	T250(636) = T249(628) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T252(648) * 4
	#moving the array point to the position of the record
	T254(652) = T253(644) + RG#(0)
	JUMP WRITEREAL
	#Calculating off set of the record in the array
	RG#(0) = T256(664) * 4
	#moving the array point to the position of the record
	T258(668) = T257(660) + RG#(0)
	JUMP WRITEREAL
	JUMP WRITEREAL
	#BEGIN ASSIGN STMT
	JUMP COUNTDOWN
	T264(692) := T266(700)
	#END ASSIGN STMT
	JUMP WRITECHAR
	JUMP WRITECHAR
	JUMP WRITEINT
	#BEGIN ASSIGN STMT
	JUMP COUNTUP
	T273(728) := T275(736)
	#END ASSIGN STMT
MAIN:
