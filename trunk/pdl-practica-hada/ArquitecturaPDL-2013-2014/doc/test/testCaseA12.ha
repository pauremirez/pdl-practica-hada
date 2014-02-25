-- recursividad 


procedure testCase12 () is
	
	cinco : Integer;

	procedure decrementa(x: out Integer) is

		y: Integer;
	begin
	
		y:=x;
		x:=x-1;
		if 0<x then
			decrementa(x);
		end if;

		Put_line(y);

	end decrementa;


begin
	
	Put_line("RECURSIVIDAD");
	Put_line("54321:");
	cinco:=5;
	decrementa(cinco);	

end testCase12;

