procedure nombrePrograma() is
	-- Declaracion de subprogramas
	
	function uno() return Integer is
	begin
		return 1;
	end uno;
	
	function suma (x, y: Integer) return Integer is
		z: Integer;
	begin
		z := x+y;
		return z;
	end suma;
	
	function mayor (x,y: Integer) return Boolean is
	begin
		return x>y;
	end mayor;
	
	-- Ejemplo de subprogramas anidados
	function padre (x:Integer; y:Integer) return Integer is
		z: Integer;
		procedure hijo (a:Integer) is
		begin
			Put_line (a); -- Muestra el valor de a
		end hijo;
	begin
		z := x+y;
		hijo(z);
		return z;
	end padre;
	
	-- ERROR: function dos faltan los parentesis ()
--	function dos return Integer is
--	begin
--		return 2;
--	end dos;
	
	--ERROR: function tres debe devolver un tipo primitivo
--	function tres () return Tpersona is
--	begin
--		return p1;
--	end tres;
	
	-- ERROR: function cuatro falta sentencia return
--	function cuatro (a:Integer; b:Integer) return Integer is
--		y:Boolean;
--	begin
--		y:=a and b;
--	end cuatro;

begin
	-- Lista de sentencias
end nombrePrograma;