procedure nombrePrograma() is
	-- Declaracion de procedimientos
	
	-- Paso por valor
	function suma (a, b: Integer) return Integer is
	begin
		z :=x+y;
		return z;
	end suma;
	
	-- Paso por referencia
	procedure mayor (x, y: out Integer; a: out Boolean) is
	begin
		a :=x>y;
	end mayor;

begin
	-- Lista de sentencias
end nombrePrograma;