// Agent jogo in project buraco

/* Initial beliefs and rules */

vercartas([], C):- false.
vercartas([H|T], C):- C==H.
vercartas([H|T], C):- C\==H & vercartas(T, C).

/* Initial goals */

!start.

+!start: 
	player(P) 	& 
	.my_name(P) 
	<- 
		.print("Eu começo!"); 
		?mesa(M);
		.print("Mesa inicial: ", M);
	.
	
+!start: true.


+player(P):
	.my_name(P) 
	<- 
		.wait(2000); 
		jogar; 
		?carta(C); 
		?mesa(M); 
		.print("Mesa atual: ", M);
		.print("Peguei um ", C); 
		!verminhamao;
		!verificarmesa; 
		!verificarmao;
	 .


// Verifica a própria mão.
+!verminhamao: 
	my_id(I) 	& 
	I==0 		& 
	maoj1(M1) 	& 
	carta(C) 	& 
	vercartas(M1,C) 
	<- 
		pegarCarta(C,I);
		.print("Coloquei o ", C, " no topo das minhas cartas.");
	.

+!verminhamao: 
	my_id(I) 	& 
	I==1 		& 
	maoj1(M2) 	& 
	carta(C) 	& 
	vercartas(M2,C) 
	<- 
		pegarCarta(C,I);
		.print("Coloquei o ", C, " no topo das minhas cartas.");
	.

+!verminhamao: true.	 
	 	
	 	
// Verifica as cartas viradas pra cima na mesa.
+!verificarmesa: 
	my_id(I) 	& 
	mesa(M) 	& 
	carta(C)	& 
	vercartas(M,C)
	<-  
		.print("Peguei um ", C, " da mesa.");
		pegarDaMesa(C, I);
	.
	
+!verificarmesa: true.
	 
	 
// Verifica a mão do oponente
+!verificarmao: 
	my_id(I) 	& 
	I==0 		& 
	maoj2(M2) 	& 
	carta(C) 	& 
	vercartas(M2, C)
	<-
		pegarMao(C, I);
		again(I);
	.
	
+!verificarmao: 
	my_id(I) 	& 
	I==1 		& 
	maoj1(M1) 	& 
	carta(C)	&
	vercartas(M1, C)
	<- 
		pegarMao(C, I);
		again(I);
	.
	
+!verificarmao:
	my_id(I) 		&
	maoj1(M1) 		&
	maoj2(M2) 		&
	mesa(M)			&
	carta(C)		& (
	vercartas(M1,C) |
	vercartas(M2,C) |
	vercartas(M,C)
	)
	<- 
		
		.print("Sou eu denovo.");
		again(I);
	.
	
+!verificarmao: 
	carta(C)
	<-
		.print("Coloquei um ", C, " na mesa."); 
		devolverCarta(C);
		proximo;
	.


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
