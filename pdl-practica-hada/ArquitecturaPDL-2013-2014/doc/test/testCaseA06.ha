-- if con else operadores

procedure testCase06 () is


        x: Integer;
	y: Integer;
	a: Boolean;

begin
	Put_line("IF ELSE OPERADORES");
	
        x:=2;
        y:=1;
 
	-- false
	a := x<y;  

        Put_line("x (falso):");
     
	if x /= 2 then
                Put_line("cierto");
        else
                Put_line("falso");
	end if;

	
	Put_line("y (cierto):");
	if y < 2 then 
                Put_line("cierto");
        else 
                Put_line("falso");
	end if;


	Put_line("a (falso):");
	if a and True then
		Put_line("cierto");	
	else 
		Put_line("falso");
	end if;

	-- true
	a := y <x;
	Put_line("a (cierto):");
	if a and True then
		Put_line("cierto");	
	else 
		Put_line("falso");
	end if;

end testCase06;