package compiler.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeSimple;
import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.code.MemoryDescriptorIF;
import es.uned.lsi.compiler.code.RegisterDescriptorIF;
import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

/**
 * Class for the ENS2001 Execution environment.
 */

public class ExecutionEnvironmentEns2001 implements ExecutionEnvironmentIF
{    
	
	/*************************
     * lista es un Array de arrays de argumentos de llamada. Se inicializa en el INICIO_ARGUMENTOS y 
     * contiene las intruscciones ensanblador para la carga de Argumentos.
     * Necesario si los par�metros son llamadas a funciones / procedimientos
     */
    ArrayList[] lista = new ArrayList[30];
    ArrayList listaPorReferencia = new ArrayList();
    
    /****************
     * Indice de la lista en laa que est�n cargada los argumentos del array lista. 
     * Toma el valor -1 se incrementa antes de ser usada, tomando valor 0.
     */
    int contadorLlamadas = -1;
    // HashMap parPorReferencia = new HashMap();
            
    List cadenas = new ArrayList();
    HashMap tablaScopes = new HashMap();
//    public final String SALTO_LINEA = "\n";
    
    private final static int      MAX_ADDRESS = 65535; 
    private final static String[] REGISTERS   = {
       ".PC",".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    };
    
    List argumentos;
   
    private RegisterDescriptorIF registerDescriptor;
    private MemoryDescriptorIF   memoryDescriptor;
   
    /**
     * Constructor for ENS2001Environment.
     */
    public ExecutionEnvironmentEns2001 ()
    {       
        super ();
    }
    
    /**
     * Returns the size of the type within the architecture.
     * @return the size of the type within the architecture.
     */
    @Override
    public final int getTypeSize (TypeSimple type)
    {      
        return 1;  
    }
    
    /**
     * Returns the registers.
     * @return the registers.
     */
    @Override
    public final List<String> getRegisters ()
    {
        return Arrays.asList (REGISTERS);
    }
    
    /**
     * Returns the memory size.
     * @return the memory size.
     */
    @Override
    public final int getMemorySize ()
    {
        return MAX_ADDRESS;
    }
           
    /**
     * Returns the registerDescriptor.
     * @return Returns the registerDescriptor.
     */
    @Override
    public final RegisterDescriptorIF getRegisterDescriptor ()
    {
        return registerDescriptor;
    }

    /**
     * Returns the memoryDescriptor.
     * @return Returns the memoryDescriptor.
     */
    @Override
    public final MemoryDescriptorIF getMemoryDescriptor ()
    {
        return memoryDescriptor;
    }

    /**
     * Translate a quadruple into a set of final instruction according to 
     * execution environment. 
     * @param cuadruple The quadruple to be translated.
     * @return A String containing the set (lines) of specific environment instructions. 
     */
    public String translate (QuadrupleIF quadruple)
    {      
        //TODO: Student work
        String oper = quadruple.getOperation();
        String rdo =null;
        if (oper.equals("INICIO_PROGRAMA")) { rdo=traducir_INICIO_PROGRAMA(quadruple);
        }else if (oper.equals("FIN_PROGRAMA")){ rdo=traducir_FIN_PROGRAMA(quadruple);
        }else if (oper.equals("CADENA")){ rdo=traducir_CADENA(quadruple);
        }else if (oper.equals("SUB")){ rdo=traducir_SUB(quadruple);
        }else if (oper.equals("MUL")){ rdo=traducir_MUL(quadruple);
        }else if (oper.equals("ADD")){ rdo=traducir_ADD(quadruple);
        }else if (oper.equals("CMP")){ rdo=traducir_CMP(quadruple);
        }else if (oper.equals("MV")){ rdo=traducir_MV(quadruple);
        }else if (oper.equals("INL")){ rdo=traducir_INL(quadruple);
        }else if (oper.equals("BZ")){ rdo=traducir_BZ(quadruple);
        }else if (oper.equals("BNZ")){ rdo=traducir_BNZ(quadruple);
        }else if (oper.equals("BN")){ rdo=traducir_BN(quadruple);        
        }else if (oper.equals("BR")){ rdo=traducir_BR(quadruple);
        }else if (oper.equals("BP")){ rdo=traducir_BP(quadruple);
        }else if (oper.equals("CALL")){ rdo=traducir_CALL(quadruple);
        }else if (oper.equals("ARGUMENTO")){ rdo=traducir_ARGUMENTO(quadruple);
        }else if (oper.equals("ARGUMENTO_REF")){ rdo=traducir_ARGUMENTO_REF(quadruple);
        }else if (oper.equals("INICIO_ARGUMENTOS")){ rdo=traducir_INICIO_ARGUMENTOS(quadruple);
        }else if (oper.equals("RET")){ rdo=traducir_RETURN(quadruple);
        }else if (oper.equals("INICIO_SUBPROG")){ rdo=traducir_INICIO_SUBPROG(quadruple);
        }else if (oper.equals("FIN_SUBPROG")){ rdo=traducir_FIN_SUBPROG(quadruple);
        }else if (oper.equals("WRSTR")){ rdo=traducir_WRSTR(quadruple);
        }else if (oper.equals("WRINT_ARRAY")){ rdo=traducir_WRINT_ARRAY(quadruple);
        }else if (oper.equals("WRINT")){ rdo=traducir_WRINT(quadruple);
        }else if (oper.equals("NOP")){ rdo="NOP";
        }else if (oper.equals("ASIGN_VECTOR")){ rdo=traducir_ASIGN_VECTOR(quadruple);
        }else if (oper.equals("VALOR_VECTOR")){ rdo=traducir_VALOR_VECTOR(quadruple);
        }
        return rdo;
    }
    
	
    private String traducir_INICIO_PROGRAMA (QuadrupleIF quadruple) {
        // Inicializar la lista de llamadas
        for (int k=0; k<30;k++){
            lista[k]=new ArrayList();
        }
        Variable var = (Variable) quadruple.getResult();
        Value valor = (Value) quadruple.getFirstOperand();
        this.tablaScopes = var.getTablaDespl();
        int tRA=0;
        
        tRA = Integer.parseInt(valor.getValue().toString());
        String trad="; INICIO PROGRAMA PRINCIPAL HAda \n"+
                         "RES "+tablaScopes.size()+1+"\n"+   
                         "MOVE #"+MAX_ADDRESS +" , "+ REGISTERS[1]+"\n"+
                         "MOVE "+ REGISTERS[1]+" , "+ REGISTERS[4]+"\n"+
                         "SUB  "+ REGISTERS[1]+" , #"+tRA+"\n"+
                         "MOVE "+ REGISTERS[5] + " , "+ REGISTERS[1]+"\n"+
                         "MOVE "+ REGISTERS[4]+" , /0";
        return trad;
    }
    private String traducir_FIN_PROGRAMA(QuadrupleIF quadruple){
        String trad="HALT \n";
        trad = trad + "\n; Inicio Cadenas de Texto\n";
        return trad;
    }
    
    private String traducir_CADENA(QuadrupleIF quadruple){
        String trad = "";
        String etiqueta = quadruple.getResult().toString(); 
        String operador = quadruple.getFirstOperand().toString();
        trad = etiqueta + ": DATA "  + operador;
        return trad;
    }
    
    private String traducir_SUB(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        OperandIF rdo= quadruple.getResult();
        String operador1="";
        String operador2="";
        String resultado="";

        // Primer Operador
        if (oper1 instanceof Value) {
            Value cte = (Value) oper1;
            operador1 = "#" + cte.getValue();
        } else {
            if (oper1 instanceof Variable) {
                Variable var = (Variable) oper1;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +"\n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
            } else {
                Temporal temp = (Temporal) oper1;
                int desp = temp.getDesplazamiento();
                operador1 = "#-" + desp + "["+REGISTERS[4]+"]";  
            }
        }
        // Segundo Operador
        if (oper2 instanceof Value) {
            Value cte = (Value) oper2;
            operador2 = "#" + cte.getValue();
        } else {
            if (oper2 instanceof Variable) {
                Variable var = (Variable) oper2;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador2 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {           
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   operador2 = ""+REGISTERS[10] +"";
                }
            } else {
                Temporal temp = (Temporal) oper2;
                int desp = temp.getDesplazamiento();
                operador2 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        // Resultado RESTA
        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            resultado = "#" + cte.getValue();
        } else {
            if (rdo instanceof Variable) {
                Variable var = (Variable) rdo;
                SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
                resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
             } else{
                Temporal temp=(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado="#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        
        trad=trad+"SUB " + operador1 + ", " + operador2 + "\n";
        trad=trad+"MOVE " +REGISTERS[5]+", " + resultado+ "\n"; //Resultado en .A
        trad=trad+"MOVE " +REGISTERS[5]+", " + REGISTERS[15]+ "\n"; //Resultado en .R9
        
        return trad;
    }
    private String traducir_ADD(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        OperandIF rdo= quadruple.getResult();
        
        String operador1="";
        String operador2="";
        String resultado="";
        
        // Primer Operador
        if (oper1 instanceof Value) {
            Value cte = (Value) oper1;
            operador1 = "#" + cte.getValue();
        } else {
            if (oper1 instanceof Variable) {
                Variable var = (Variable) oper1;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
            } else {
                Temporal temp = (Temporal) oper1;
                int desp = temp.getDesplazamiento();
                operador1 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        // Segundo Operador
        if (oper2 instanceof Value) {
            Value cte = (Value) oper2;
            operador2 = "#" + cte.getValue();
        } else {
            if (oper2 instanceof Variable) {
                Variable var = (Variable) oper2;
                
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador2 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   operador2 = ""+REGISTERS[10] +"";
                }
            } else {
                Temporal temp = (Temporal) oper2;
                int desp = temp.getDesplazamiento();
                operador2 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        
        // Resultado SUMA
        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            resultado = "#" + cte.getValue();
        } else {
            if (rdo instanceof Variable) {
                Variable var = (Variable) rdo;
                SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
                resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
            } else{
                Temporal temp=(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado="#-" + desp + "["+REGISTERS[4]+"]";
            }
        }

        trad=trad+"ADD " + operador1 + ", " + operador2 + "\n";
        trad= trad + "MOVE " + ""+REGISTERS[5]+", " + resultado+"\n";
        return trad;
    }
    
    private String traducir_MUL(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        OperandIF rdo= quadruple.getResult();
        
        String operador1="";
        String operador2="";
        String resultado="";
        
        // Primer Operador
        if (oper1 instanceof Value) {
            Value cte = (Value) oper1;
            operador1 = "#" + cte.getValue();
        } else {
            if (oper1 instanceof Variable) {
                Variable var = (Variable) oper1;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
            } else {
                Temporal temp = (Temporal) oper1;
                int desp = temp.getDesplazamiento();
                operador1 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        // Segundo Operador
        if (oper2 instanceof Value) {
            Value cte = (Value) oper2;
            operador2 = "#" + cte.getValue();
        } else {
            if (oper2 instanceof Variable) {
                Variable var = (Variable) oper2;
                
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador2 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   operador2 = ""+REGISTERS[10] +"";
                }
            } else {
                Temporal temp = (Temporal) oper2;
                int desp = temp.getDesplazamiento();
                operador2 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        
        // Resultado MULTIPLICACION
        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            resultado = "#" + cte.getValue();
        } else {
            if (rdo instanceof Variable) {
                Variable var = (Variable) rdo;
                SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
                resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
            } else{
                Temporal temp=(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado="#-" + desp + "["+REGISTERS[4]+"]";
            }
        }

        trad=trad+"MUL " + operador1 + ", " + operador2 + "\n";
        trad= trad + "MOVE " + ""+REGISTERS[5]+", " + resultado+"\n";
        return trad;
    }
    
    private String traducir_CMP(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        
        OperandIF oper1 = quadruple.getResult();
        OperandIF oper2 = quadruple.getFirstOperand();
        String operador1="";
        String operador2="";
        
        // Primer OPERANDO
        if (oper1 instanceof Value){
            Value cte=(Value) oper1;
            operador1="#" + cte.getValue();
         }else{
            if (oper1 instanceof Variable){
                Variable var=(Variable) oper1;
                SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
            }else{
                Temporal temp = (Temporal) oper1; 
                operador1="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
            }
        }
        // Segundo OPERANDO
        if (oper2 instanceof Value){
            Value cte=(Value) oper2;
            operador2="#" + cte.getValue();
         }else{
            if (oper2 instanceof Variable){
                Variable var=(Variable) oper2;
                SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador2 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else { 
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   operador2 = ""+REGISTERS[10] +"";
                }
            }else{
                Temporal temp = (Temporal) oper2; 
                operador2="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
            }
        }
        trad = trad + "CMP " + operador1 + ", " + operador2 ;
        return trad;
    }
    
    private String traducir_MV(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF rdo= quadruple.getResult();
        
        String operador1="";
        String resultado="";
        
        // Primer Operador
        if (oper1 instanceof Value) {
            Value cte = (Value) oper1;
            operador1 = "#" + cte.getValue();
        } else {
            if (oper1 instanceof Variable) {
            
                Variable var = (Variable) oper1;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
            } else {
                Temporal temp = (Temporal) oper1;
                int desp = temp.getDesplazamiento();
                operador1 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        
        // Segundo operando
        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            resultado = "#" + cte.getValue();
        } else {
            if (rdo instanceof Variable) {
                Variable var = (Variable) rdo;
                
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar.getDesplazamiento()+"\n";
                   resultado="["+REGISTERS[5]+"]";
                }
            } else{
                Temporal temp=(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado="#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        trad= trad + "MOVE " + operador1 + ", " + resultado;
        
        return trad;
 
    }

    private String traducir_INL(QuadrupleIF quadruple){
        OperandIF rdo = quadruple.getResult();
        String trad = "; Etiqueta de salto " + rdo+"\n";
        //trad = trad + cambiarEtiqueta(rdo.toString()) + " :";
        trad = trad + rdo.toString() + " :";
        return trad;
    }
    private String traducir_BZ(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si cero a etiqueta " + rdo+"\n";
        trad = trad + "BZ /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BNZ(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si no cero a etiqueta " + rdo+"\n";
        trad = trad + "BNZ /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BN(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si negativo " + rdo+"\n";
        trad = trad + "BN /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BR(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto incondicional " + rdo+"\n";
        trad="BR /" + rdo.toString(); 
        return trad;
    }

    private String traducir_BP(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si positiv0 " + rdo+"\n";
        //trad = trad + "BP /" + cambiarEtiqueta(rdo.toString()); 
        trad = trad + "BP /" + rdo.toString(); 
        return trad;
    }

    private String traducir_CALL(QuadrupleIF quadruple){
        
        String trad="; Llamada Funcion "+quadruple+"\n";
        Variable rdo = (Variable) quadruple.getResult();
        Value valor = (Value) quadruple.getFirstOperand();
        int nivel = Integer.parseInt(valor.getValue().toString());
        OperandIF oper2 = quadruple.getSecondOperand();
        
        String etiqRet = rdo.getEtiqRetorno().toString();
        String etiq = rdo.getEtiqSub().toString();
        
        // Desplazamiento
        Integer d = (Integer)this.tablaScopes.get(rdo.getName());
        int despl = d.intValue();
        
        trad =trad+"MOVE "+REGISTERS[1]+" , "+REGISTERS[3]+"\n";
        
        // Argumentos
        for (int i = 1; i < lista[contadorLlamadas].size(); i++) {
        	String s = (String) lista[contadorLlamadas].get(i);
        	trad=trad+s+"\n";
        }
        
        // una vez que se ha imprimido esa lita, hay que vaciarla
        lista[contadorLlamadas]=new ArrayList();
        
        // Areglo ambito
        trad=trad+"MOVE /"+nivel +" , #-2[" + REGISTERS[4] + "]\n";        
        trad=trad+"MOVE "+REGISTERS[4]+" , /"+nivel+"\n";        
        
        trad= trad+"MOVE "+REGISTERS[4]+" , "+REGISTERS[3]+"\n";
        trad= trad+"MOVE "+REGISTERS[1] +" , "+REGISTERS[4]  +"\n";
        
        // Consideramos variables y temporales
        trad=trad+"SUB "+REGISTERS[1]+" , #" + despl+"\n";  
        trad=trad+"MOVE "+REGISTERS[5]+" , "+REGISTERS[1]+"\n";
        trad=trad+"MOVE #RET_"+ etiqRet  +" , #-1[" + REGISTERS[4] + "]\n";     
        trad=trad+"MOVE "+REGISTERS[3]+" , #-3[" + REGISTERS[4] + "]\n";        
        trad=trad+"MOVE #REF_"+ etiqRet  +" , #-4["+REGISTERS[4]+"]\n";
        
        // Salto a la funcion 
        trad=trad+"BR /"+ etiq   +"\n";
        
        // Parametros referencia (copia-valor)
        trad=trad+"REF_"+ etiqRet  +":\n";
        for (int i=0; i<listaPorReferencia.size(); i++){
            String s = (String) listaPorReferencia.get(i);
            trad=trad+s+"\n";
        }
        listaPorReferencia.clear();
        
        trad=trad+"BR /REF_"+ etiq   +"\n";

        // Etiqueta Retorno llamada
        trad=trad+"RET_"+ etiqRet+": \n";
        
        // Cuando es un procedimiento esta sentencia no se implementa
        if (oper2 instanceof Temporal) {
            Temporal operando2 = (Temporal) oper2;
            trad=trad+"MOVE "+REGISTERS[15] +" , #-"+ operando2.getDesplazamiento()+"["+REGISTERS[4]+"]\n";
        } else {
            //trad=trad+"NOP \n";
            trad=trad+"MOVE .R9 , #-5[.IY]\n";
        }    
        contadorLlamadas--;
        return trad;      
        
    }
     private String traducir_RETURN(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo = quadruple.getResult();
        String resultado="";

        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            resultado = "#" + cte.getValue();
        } else {
            if (rdo instanceof Variable) {
                Variable var = (Variable) rdo;
                SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
                resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
            } else{
                Temporal temp=(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado="#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        
        trad= trad+ "MOVE " + resultado + " , #-3[" + REGISTERS[3] + "]\n";
        trad = trad +"MOVE #-1["+REGISTERS[4]+"] , "+REGISTERS[0]+"\n"; 

        return trad;
     }
 
    private String traducir_INICIO_SUBPROG(QuadrupleIF quadruple){
        String trad=";-- Definicion FUNCION/PROCEDIMIENTO \n";
        OperandIF rdo = (OperandIF) quadruple.getResult();        
        
        String etiq = rdo.toString();
        trad=trad+"BR /FIN_"+etiq+"\n";
        trad=trad +etiq + " :\n";
        return trad;      
}
    
    private String traducir_FIN_SUBPROG(QuadrupleIF quadruple){
        String trad="";
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        Value valor = (Value) quadruple.getSecondOperand();
        int nivel = Integer.parseInt(valor.getValue().toString());
        
        String etiqFin = oper1.toString();
        trad=trad+etiqFin+" : \n";
        trad=trad+"; Retorno Argumentos REFERENCIA \n";
        trad=trad+"MOVE #-4["+REGISTERS[4]+"] , "+REGISTERS[11] +" \n"; 
        trad=trad+"MOVE "+REGISTERS[11] +" , "+REGISTERS[0]+" \n";
        trad=trad+"REF_"+rdo.toString()+": \n";
                
        trad=trad+"; Retorno Subprograma \n";
        trad=trad+"MOVE #-1[" + REGISTERS[4] + "] , "+REGISTERS[13]+"\n";
        
        // Arreglo ambitos
        trad=trad+"MOVE #-2[" + REGISTERS[4] + "] , /"+nivel+"\n";
        
        trad=trad+"MOVE "+REGISTERS[4]+" , "+REGISTERS[1]+"\n";
        trad=trad+"MOVE "+REGISTERS[3]+" , "+REGISTERS[4]+"\n";
        trad=trad+"MOVE #-3[" + REGISTERS[4] + "] , "+REGISTERS[3]+"\n";        
        trad=trad+"MOVE "+REGISTERS[13] +" , "+REGISTERS[0]+"\n";
        
        trad=trad+ "MOVE .A, #-5[.IY] \n"; //Arreglo para imprimir el dato como WRTINT
        
        trad=trad+"FIN_"+rdo.toString()+" : \n";
        return trad;      
}
    
        private String traducir_INICIO_ARGUMENTOS(QuadrupleIF quadruple){
        contadorLlamadas++;
        Integer i = Integer.valueOf(0);
        
        lista[contadorLlamadas].add(i); 
        return "; INICIO ARGUMENTOS FIN";
}

    private String traducir_ARGUMENTO(QuadrupleIF quadruple){
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        
        // Desplazamiento
        Value operando1 = (Value) oper1;
        int despl = Integer.parseInt(operando1.getValue().toString());
        // Parametros
        Integer numArg = (Integer) lista[contadorLlamadas].get(0);
    	int offsetParametro = numArg.intValue();
        // Evaluamos argumentos
        offsetParametro++;
        Integer p = Integer.valueOf(offsetParametro);
        lista[contadorLlamadas].remove(0);
        lista[contadorLlamadas].add(0, p);
        
        if (rdo instanceof Value) {
            Value cte = (Value) rdo;
            String str = "MOVE #"+cte.getValue() +" , #-" + despl+ "["+REGISTERS[3]+"]";
            lista[contadorLlamadas].add(str);
        }else{    
            if (rdo instanceof Variable){
                Variable var = (Variable) rdo;
                SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                String str="";
                // Ambito variable
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   str = "MOVE #-"+SimVar.getDesplazamiento() + "["+REGISTERS[4]+"] , #-" + despl+ "["+REGISTERS[3]+"]";
                }else {
                   // Variable en otro ambito 
                   str="MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   str=str+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   str=str+"MOVE [REGISTERS[5]] , #-"+ despl+ "["+REGISTERS[3]+"] \n";
                }
                lista[contadorLlamadas].add(str);
            }else {
                Temporal temp = (Temporal) rdo;
                String str = "MOVE #-"+temp.getDesplazamiento() + "["+REGISTERS[4]+"] , #-" + despl+ "["+REGISTERS[3]+"]";
                lista[contadorLlamadas].add(str);
            }
        }
        return "; Cargado argumento "+quadruple;
}
    
    private String traducir_ARGUMENTO_REF(QuadrupleIF quadruple){
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        
        // Desplazamiento
        Value operando1 = (Value) oper1;
        int despl = Integer.parseInt(operando1.getValue().toString());
        // Parametros
        Integer numArg = (Integer) lista[contadorLlamadas].get(0);
    	int offsetParametro = numArg.intValue();
        // Evaluamos argumentos
        offsetParametro++;
        Integer p = Integer.valueOf(offsetParametro);
        lista[contadorLlamadas].remove(0);
        lista[contadorLlamadas].add(0, p);
        
        // Por referencia solo puede ser variable (ni temporal, ni valor)
        Variable var = (Variable) rdo;
        SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                
        String str="";
        String str2="";
        // Ambito variable
        if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
            str = "MOVE #-"+SimVar.getDesplazamiento() + "["+REGISTERS[4]+"] , #-" + despl+ "["+REGISTERS[3]+"]";
            str2 = "MOVE #-"+ despl+ "["+REGISTERS[4]+"] , #-"+ SimVar.getDesplazamiento()+ "["+REGISTERS[3]+"]";
        }else {
            // Variable en otro ambito 
            str="MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
            str=str+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
            str=str+"MOVE [REGISTERS[5]] , #-"+ despl+ "["+REGISTERS[3]+"] \n";
            
            // Parametro copia/valor
            str2="MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
            str2=str2+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
            str2=str2+"MOVE #-"+despl +"["+REGISTERS[4]+"] , [.A]";
            
        }
        // Guarda instruciones ensamblador de los par REFERENCIADOS        
        lista[contadorLlamadas].add(str);
        listaPorReferencia.add(str2);
             
        return "; Cargado argumento REFERENCIA "+quadruple;
    }
   
    private String traducir_WRINT_ARRAY (QuadrupleIF quadruple) {
        String resultado = "";
        String trad= "";
        OperandIF rdo=quadruple.getResult();
        
        Temporal temp =(Temporal) rdo; 
        int desp=temp.getDesplazamiento();
        resultado = "#-" + (desp-12) + "["+REGISTERS[4]+"]"; //Ajuste de los desplazamientos para los vectores
        
       trad= trad+"WRINT "+resultado + "\nWRCHAR #10\nWRCHAR #13";    
       return trad;
    } 
    
    private String traducir_WRINT (QuadrupleIF quadruple) {
        String resultado = "";
        String trad= "";
        OperandIF rdo=quadruple.getResult();
        
        if(rdo  instanceof Value){
            Value cte=(Value) rdo;
            resultado = "#" + cte.getValue();
        }else{
            if(rdo instanceof Variable){
                Variable var=(Variable) rdo;
                SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                 resultado = "#-" + SimVar.getDesplazamiento()+ "["+REGISTERS[4]+"]";
               }else {
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE [REGISTERS[5]] , "+REGISTERS[8] +" \n";
                   resultado = ""+REGISTERS[8] +"";
                }
            }else{
                Temporal temp =(Temporal) rdo; 
                int desp=temp.getDesplazamiento();
                resultado = "#-" + (desp) + "["+REGISTERS[4]+"]"; 
            }
       }
       trad= trad+"WRINT "+resultado + "\nWRCHAR #10\nWRCHAR #13";    
       return trad;
    } 
    
    // Escribir una cadena
     private String traducir_WRSTR(QuadrupleIF quadruple) {
    	 String trad = "";
         String operador = quadruple.getResult().toString();
         OperandIF o = quadruple.getFirstOperand();
         trad= "WRSTR /" + operador + "\nWRCHAR #10\nWRCHAR #13"; 
         
         return trad;

    }
   
     private String traducir_ASIGN_VECTOR(QuadrupleIF quadruple){
         String trad= "; Traducir "+quadruple.toString() +"\n";
         
         OperandIF oper1= quadruple.getFirstOperand(); //indice
         OperandIF oper2= quadruple.getSecondOperand(); //valor
         OperandIF rdo= quadruple.getResult(); //Vector
         String operador1= "";
         String operador2="";
         String resultado="";
         int desplazamientoOper1=0;
         
         //Indice del vector
         if (oper1 instanceof Value){
             Value cte = (Value) oper1;
             operador1 = "#" + cte.getValue();
             desplazamientoOper1=Integer.parseInt(cte.getValue().toString());
         }
         //Expresion a asignar al vector
         if (oper2 instanceof Value){
             Value cte3 = (Value) oper2;
             operador2 = "#" + cte3.getValue();
         }
         
         //Variable Vector
         if (rdo instanceof Variable) {
             Variable var = (Variable) rdo;
             SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
             SimVar.setSize(var.getSize());
             resultado = "#-" + (SimVar.getDesplazamiento()+desplazamientoOper1) + "["+REGISTERS[4]+"]"; 
         }

         trad = trad + "MOVE "+operador2+" , "+resultado +"\n";
         
         return trad;      
     } 

     private String traducir_VALOR_VECTOR(QuadrupleIF quadruple){
         String trad= "; Traducir "+quadruple.toString() +"\n";
         
         OperandIF oper1= quadruple.getFirstOperand(); //indice
         OperandIF rdo= quadruple.getResult(); //Vector
         String operador1= "";
         String resultado="";
         int desplazamientoOper1=0;
         
         //Indice del vector
         if (oper1 instanceof Value){
             Value cte = (Value) oper1;
             operador1 = "#" + cte.getValue();
             desplazamientoOper1=Integer.parseInt(cte.getValue().toString());
         }
    
         //Variable Vector
         if (rdo instanceof Variable) {
             Variable var = (Variable) rdo;
             SymbolVariable SimVar = (SymbolVariable) var.getScope().getSymbolTable().getSymbol(var.getName());
             SimVar.setSize(var.getSize());
             resultado = "#-" + (SimVar.getDesplazamiento()+desplazamientoOper1) + "["+REGISTERS[4]+"]"; 
         }
         
         trad = trad + "MOVE "+resultado+" , "+REGISTERS[5]+"\n";
         trad = trad + "MOVE "+REGISTERS[5]+" , #0["+REGISTERS[4]+"]\n";
         
         return trad;      
     }  
}
