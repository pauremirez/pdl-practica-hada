-- procedimientos paso por referencia

procedure testCase09 () is

w: Integer;
a,b: Integer;

procedure resta (x,y,z: out Integer) is
begin
		
	z:=x-y;	

end resta;


begin

	Put_line ("SUBPROGRAMAS PROCEDIMIENTO");
	a:= 5;
	b:= 3;
--	resta (a,b,w);
	Put_line("w(2):");
	Put_line(w);
			
end testCase09;
