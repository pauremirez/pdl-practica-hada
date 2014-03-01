-- tipos estructurados VECTORES


procedure testCase07 () is

	type VectorE is array (1..10) of Integer;
	type VectorL is array (1..3) of Boolean;

	
	vE : VectorE;
	vL : VectorL;
begin
	
	Put_line("VECTORES");	

	vE(1):=20;
	vE(2):=30;
	
	Put_Line ("vE(20,30):");
--	Put_Line (vE(1));
--	Put_Line (vE(2));

	vL(1):=True;
	vL(2):=False;
	
	Put_Line ("vL(true,false):");
--	Put_Line (vL(1));
--	Put_Line (vL(2));


end testCase07;