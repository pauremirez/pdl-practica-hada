-- Control de flujo WHILE

procedure testCase10 () is

        x: Integer;

begin	
        Put_line ("CRONTROL FLUJO WHILE");
	
	x:=5;
	Put_line("x(5,4,3,2):");

	while 1<x loop

		Put_line(x);
		x:=x-1;

	end loop;

end testCase10;
