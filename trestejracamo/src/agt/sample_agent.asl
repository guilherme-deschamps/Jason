// Agent sample_agent in project trestejracamo

/* Initial beliefs and rules */
vermesa([], C):- false.
vermesa([H|T], C):- H==C & true.
vermesa([H|T], C):- H\==C & vermesa(T, C).

carta(5).
/* Initial goals */

!start.

/* Plans */

+!start: .my_name(jogador1) & carta(C) & mesa(M) & vermesa(M, C)<- .print("Funcionou.").
+!start <- .print("oi").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
