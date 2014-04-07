-- subprogramas procedimientos anidados


procedure testCase11 () is

	once: Integer;


	procedure proc1(x: out Integer) is 
	
	y: Integer;
	

		-- procedimiento anidado 
		procedure resta(x: out Integer) is 
		w: Integer;
		begin  
			-- y accesible
			w:= y-x;
			Put_line("w(6):");  
			Put_line(w);   
		end resta; 

	begin  
		y:=5;
		resta(x); 		
	end proc1; 


begin

	Put_line ("PROCEDIMIENTOS ANIDADOS");
	once:=11;
	proc1 (once);

end testCase11;
