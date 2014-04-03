package compiler.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeArray;
import compiler.semantic.type.TypeSet;
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
    public final String SALTO_LINEA = "\n";
    
    private final static int      MAX_ADDRESS = 65535; 
    private final static String[] REGISTERS   = {
       ".PC",".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    };
    
    List argumentos;
    public static final int numDireccionesRA = 5;
    public static final String RA_VINCULO_CONTROL   = "#-3[" + REGISTERS[4] + "]";
    public static final String RA_VINCULO_ACCESO    = "#-2[" + REGISTERS[4] + "]";
    public static final String RA_DIRECCION_RETORNO = "#-1[" + REGISTERS[4] + "]";
    public static final String RA_VALOR_RETORNO     = "#0["  + REGISTERS[4] + "]";
    
    
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
        }else if (oper.equals("ADD")){ rdo=traducir_ADD(quadruple);
        }else if (oper.equals("CMP")){ rdo=traducir_CMP(quadruple);
        }else if (oper.equals("ACCESO_REGISTRO")){ rdo=traducir_ACCESO_REGISTRO(quadruple);
        }else if (oper.equals("ACCESO_REGISTRO_PUNTERO")){ rdo=traducir_ACCESO_REGISTRO_PUNTERO(quadruple);
        }else if (oper.equals("ACCESO_PUNTERO")){ rdo=traducir_ACCESO_PUNTERO(quadruple);
        }else if (oper.equals("DIR_MEM")){ rdo=traducir_DIR_MEM(quadruple);
        }else if (oper.equals("DIR_MEM_REGISTRO")){ rdo=traducir_DIR_MEM_REGISTRO(quadruple);
        }else if (oper.equals("MV")){ rdo=traducir_MV(quadruple);
        }else if (oper.equals("ASIG_REGISTRO")){ rdo=traducir_ASIG_REGISTRO(quadruple);
        }else if (oper.equals("ASIG_PUNTERO")){ rdo=traducir_ASIG_PUNTERO(quadruple);
        }else if (oper.equals("ASIG_REGISTRO_PUNTERO")){ rdo=traducir_ASIG_REGISTRO_PUNTERO(quadruple);
        }else if (oper.equals("ASIG_SET")){ rdo=traducir_ASIG_SET(quadruple);
        }else if (oper.equals("INL")){ rdo=traducir_INL(quadruple);
        }else if (oper.equals("BZ")){ rdo=traducir_BZ(quadruple);
        }else if (oper.equals("BNZ")){ rdo=traducir_BNZ(quadruple);
        }else if (oper.equals("BN")){ rdo=traducir_BN(quadruple);        
        }else if (oper.equals("BR")){ rdo=traducir_BR(quadruple);
        }else if (oper.equals("BRF")){ rdo=traducir_BRF(quadruple);
        }else if (oper.equals("BRT")){ rdo=traducir_BRT(quadruple);
        }else if (oper.equals("BP")){ rdo=traducir_BP(quadruple);
        }else if (oper.equals("INC")){ rdo=traducir_INC(quadruple);
        }else if (oper.equals("CALL")){ rdo=traducir_CALL(quadruple);
        }else if (oper.equals("ARGUMENTO")){ rdo=traducir_ARGUMENTO(quadruple);
        }else if (oper.equals("ARGUMENTO_REF")){ rdo=traducir_ARGUMENTO_REF(quadruple);
        }else if (oper.equals("INICIO_ARGUMENTOS")){ rdo=traducir_INICIO_ARGUMENTOS(quadruple);
        }else if (oper.equals("RETORNO")){ rdo=traducir_RETORNO(quadruple);
        }else if (oper.equals("INICIO_SUBPROG")){ rdo=traducir_INICIO_SUBPROG(quadruple);
        }else if (oper.equals("FIN_SUBPROG")){ rdo=traducir_FIN_SUBPROG(quadruple);
        }else if (oper.equals("INICIAR_SET")){ rdo=traducir_INICIAR_SET(quadruple);
        }else if (oper.equals("CARGAR_SET")){ rdo=traducir_CARGAR_SET(quadruple);
        }else if (oper.equals("IN_SET")){ rdo=traducir_IN_SET(quadruple);
        }else if (oper.equals("UNION_SET")){ rdo=traducir_UNION_SET(quadruple);
        }else if (oper.equals("INL")){ rdo=traducir_INL(quadruple);
        }else if (oper.equals("WRSTR")){ rdo=traducir_WRSTR(quadruple);
        }else if (oper.equals("WRINT")){ rdo=traducir_WRINT(quadruple);
        }else if (oper.equals("NOP")){ rdo="NOP";
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
        
        Integer i = (Integer)this.tablaScopes.get(var.getName());
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
        // la etiqueta
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
        trad=trad+"MOVE " + ""+REGISTERS[5]+", " + resultado;
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
        trad= trad + "MOVE " + ""+REGISTERS[5]+", " + resultado;
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
                // operador1="#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
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
    private String traducir_ACCESO_REGISTRO(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        OperandIF oper2 = quadruple.getSecondOperand();
        String resultado="";
        
        Variable var2=(Variable) oper2;
        Variable var=(Variable) oper1;
        SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        Temporal temp = (Temporal) rdo; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";

        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad= trad+"SUB "+REGISTERS[4]+" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+resultado;
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+resultado;
        }

        return trad;
    }
    private String traducir_ACCESO_REGISTRO_PUNTERO(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        OperandIF oper2 = quadruple.getSecondOperand();
        String resultado="";
        
        Variable var2=(Variable) oper2;
        Variable var=(Variable) oper1;
        SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        Temporal temp = (Temporal) rdo; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";

        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad= trad+"SUB "+REGISTERS[4]+" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #0 \n";
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+resultado;
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
            trad= trad+"SUB "+REGISTERS[8] +" , #0 \n";
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+resultado;
            
        }

        return trad;
    }
    
    private String traducir_ACCESO_PUNTERO(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        
        Variable var = (Variable) oper1;
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        Temporal temp = (Temporal) rdo;
        
        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad=trad+"SUB #-"+ SimVar.getDesplazamiento()+ "["+REGISTERS[4]+"] , #0 \n";
            trad=trad+"MOVE ["+REGISTERS[5]+"] , #-"+ temp.getDesplazamiento()+ "["+REGISTERS[4]+"]";
        } else {
            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad = trad + "SUB "+REGISTERS[7] +" , #" + SimVar.getDesplazamiento() + "\n";
            trad=trad+"MOVE ["+REGISTERS[5]+"] , #-"+ temp.getDesplazamiento()+ "["+REGISTERS[4]+"]";
            trad=trad+"SUB #-"+ temp.getDesplazamiento()+ "["+REGISTERS[4]+"] , #0 \n";
            trad=trad+"MOVE ["+REGISTERS[5]+"] , #-"+ temp.getDesplazamiento()+ "["+REGISTERS[4]+"]";
        }
        return trad;
    }
    private String traducir_DIR_MEM(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        
        Variable var = (Variable) oper1;
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());

        Temporal temp = (Temporal) rdo;
        
        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad=trad+"SUB "+REGISTERS[4]+" , #"+ SimVar.getDesplazamiento()+"\n";
            trad=trad+"MOVE "+REGISTERS[5]+" ,  #-"+temp.getDesplazamiento()+"["+REGISTERS[4]+"]";
        
        } else {
            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad = trad + "SUB "+REGISTERS[7] +" , #" + SimVar.getDesplazamiento() + "\n";
            trad=trad+"MOVE "+REGISTERS[5]+" ,  #-"+temp.getDesplazamiento()+"["+REGISTERS[4]+"]";
        }
        
        return trad;
    }
    
        private String traducir_DIR_MEM_REGISTRO(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        OperandIF oper2 = quadruple.getSecondOperand();
        String resultado="";
        
        Variable var2=(Variable) oper2;
        Variable var=(Variable) oper1;
        SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        Temporal temp = (Temporal) rdo; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";

        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad= trad+"SUB "+REGISTERS[4]+" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE "+REGISTERS[5]+" , "+resultado;
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #"+(SimVar.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE "+REGISTERS[5]+" , "+resultado;
        }

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
                   // trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   // resultado= ""+REGISTERS[10] +"";
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
  
    private String traducir_ASIG_REGISTRO(QuadrupleIF quadruple){
        String trad="; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        
        String operador2="";
        
        Variable var2=(Variable) oper1;
        
        Variable var1=(Variable) rdo;
        SymbolVariable SimVar1=(SymbolVariable) var1.getAmbito().getSymbolTable().getSymbol(var1.getName());
                
        // Segundo Operador o expresion
        if (oper2 instanceof Value) {
            Value cte = (Value) oper2;
            operador2 = "#" + cte.getValue();
        } else {
            if (oper2 instanceof Variable) {
                Variable var = (Variable) oper2;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                // El ambito donde encuentro la expresi�n recuperado del getResult()
                if ( SimVar.getScope().getName().equals(SimVar1.getScope().getName()) ){ 
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
        
        // Se trata despu�s la asignaci�n por temas del acumulador
        if (SimVar1.getScope().getName().equals(var1.getScope().getName())) {
            trad = trad+"SUB "+REGISTERS[4]+" , #"+(SimVar1.getDesplazamiento()+var2.getDesplCampo())+"\n";  
        } else {
            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar1.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #"+(SimVar1.getDesplazamiento()+var2.getDesplCampo())+"\n";  
        }
        trad= trad+"MOVE "+operador2+" , ["+REGISTERS[5]+"]";
        return trad;
    }
    
    private String traducir_ASIG_REGISTRO_PUNTERO(QuadrupleIF quadruple){
        String trad="; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        
        String operador2="";
        
        Variable var2=(Variable) oper1;
        
        Variable var1=(Variable) rdo;
        SymbolVariable SimVar1=(SymbolVariable) var1.getAmbito().getSymbolTable().getSymbol(var1.getName());
                
        // Segundo Operador o expresion
        if (oper2 instanceof Value) {
            Value cte = (Value) oper2;
            operador2 = "#" + cte.getValue();
        } else {
            if (oper2 instanceof Variable) {
                Variable var = (Variable) oper2;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                // El ambito donde encuentro la expresi�n recuperado del getResult()
                if ( SimVar.getScope().getName().equals(SimVar1.getScope().getName()) ){ 
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
        
        // Primer operando
        // Se trata despu�s la asignaci�n por temas del acumulador
        if (SimVar1.getScope().getName().equals(var1.getScope().getName())) {
            trad = trad+"SUB "+REGISTERS[4]+" , #"+(SimVar1.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";  
            trad= trad+"SUB "+REGISTERS[8] +" , #0\n";
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar1.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad= trad+"SUB "+REGISTERS[7] +" , #"+(SimVar1.getDesplazamiento()+var2.getDesplCampo())+"\n";  
            trad= trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";  
            trad= trad+"SUB "+REGISTERS[8] +" , #0\n";
        }

        trad= trad+"MOVE "+operador2+" , ["+REGISTERS[5]+"]";
        return trad;
    }
    
    private String traducir_ASIG_PUNTERO(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
        
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
                operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                if (SimVar.getScope().getName().equals(var.getScope().getName())) {
                    operador1 = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
                } else {
                    // Variable en otro ambito 
                    trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[9] +" \n";
                    trad = trad + "SUB "+REGISTERS[9] +" , #" + SimVar.getDesplazamiento() + "\n";
                    trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                    operador1 = ""+REGISTERS[10] +"";
                }
            } else {
                Temporal temp = (Temporal) oper1;
                int desp = temp.getDesplazamiento();
                operador1 = "#-" + desp + "["+REGISTERS[4]+"]";
            }
        }
        // Resultado al acumulador y movemos la expr a la direcci�n del acumulador
        Variable var = (Variable) rdo;
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
        
        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad = trad + "SUB " + resultado + ", #0 \n";
            trad = trad + "MOVE " + operador1 + ", ["+REGISTERS[5]+"]";
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad = trad + "SUB "+REGISTERS[7] +" , #" + SimVar.getDesplazamiento() + "\n";
            trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
            trad = trad + "SUB "+REGISTERS[8] +" , #0 \n";
            trad = trad + "MOVE " + operador1 + ", ["+REGISTERS[5]+"]";
        }
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
        //trad = trad + "BZ /" + cambiarEtiqueta(rdo.toString()); 
        trad = trad + "BZ /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BNZ(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si no cero a etiqueta " + rdo+"\n";
        //trad = trad + "BNZ /" + cambiarEtiqueta(rdo.toString()); 
        trad = trad + "BNZ /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BN(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto si negativo " + rdo+"\n";
        //trad = trad + "BN /" + cambiarEtiqueta(rdo.toString());
        trad = trad + "BN /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BR(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Salto incondicional " + rdo+"\n";
        //trad="BR /" + cambiarEtiqueta(rdo.toString()); 
        trad="BR /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BRF(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Si !x, salto incondicional " + rdo+"\n";
        //trad="BR /" + cambiarEtiqueta(rdo.toString()); 
        trad="BRF /" + rdo.toString(); 
        return trad;
    }
    private String traducir_BRT(QuadrupleIF quadruple){
        String trad=""; 
        OperandIF rdo= quadruple.getResult();
        trad = "; Si x, salto incondicional " + rdo+"\n";
        //trad="BR /" + cambiarEtiqueta(rdo.toString()); 
        trad="BRT /" + rdo.toString(); 
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
    private String traducir_INC(QuadrupleIF quadruple){
        String trad=""; 
        Variable var=(Variable) quadruple.getResult();
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        
        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            trad="INC #-" +SimVar.getDesplazamiento()+"["+REGISTERS[4]+"]";
        } else {

            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad = trad + "SUB "+REGISTERS[7] +" , #" + SimVar.getDesplazamiento() + "\n";
            trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
            trad = trad + "INC "+REGISTERS[8] +" \n";
            trad = trad + "MOVE "+REGISTERS[8] +" , ["+REGISTERS[5]+"]";
        }        
        return trad;
    }

    private String traducir_INICIAR_SET(QuadrupleIF quadruple) {
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo= quadruple.getResult();
        OperandIF oper1= quadruple.getFirstOperand();
   
        String resultado="";
         // Operador1: Tamanyo del conjunto
        Value tamanyo = (Value) oper1;
        
        // Resultado Var
        Variable var=(Variable) rdo;
        SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        for (int i = 0; i < Integer.parseInt(tamanyo.getValue().toString()); i++) {
            int d = SimVar.getDesplazamiento()+i;
            if (SimVar.getScope().getName().equals(var.getScope().getName())) {
                resultado = "#-" + d + "["+REGISTERS[4]+"]";
            } else {
                // Variable en otro ambito 
                trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
                trad = trad + "SUB "+REGISTERS[7] +" , #" + d + "\n";
                trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                resultado = ""+REGISTERS[8] +"";
            }
            trad = trad + "MOVE #0 , "+resultado + "\n";
        }
        return trad;
        
    }
    private String traducir_CARGAR_SET(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF oper1= quadruple.getFirstOperand();
        OperandIF oper2= quadruple.getSecondOperand();
        String resultado= "";
        
        // Resultado 
        Variable var = (Variable) quadruple.getResult();
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        TypeSet tipoSet = (TypeSet) SimVar.getType();
        
        // Primer y Segundo Operador
        Value valIni  = (Value) oper1;
        Value valFin  = (Value) oper2;
        
        for (int i=Integer.parseInt(valIni.getValue().toString()); i<=Integer.parseInt(valFin.getValue().toString()); i++){
            int d = SimVar.getDesplazamiento()+i-1;
            if (SimVar.getScope().getName().equals(var.getScope().getName())) {
                resultado = "#-" + d + "["+REGISTERS[4]+"]";
            } else {
                // Variable en otro ambito 
                trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[9] +" \n";
                trad = trad + "SUB "+REGISTERS[9] +" , #" + d + "\n";
                resultado = "["+REGISTERS[5]+"]";
            }
            trad = trad + "MOVE #1 , "+resultado+"\n";    
        }
        return trad;
    }
    private String traducir_IN_SET(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        
        OperandIF rdo   = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        OperandIF oper2 = quadruple.getSecondOperand();
        String operador1="";
        String operador2="";
        String resultado="";
        
        
        // Segundo OPERANDO: Expresion
        if (oper2 instanceof Value){
            Value cte=(Value) oper2;
            operador2="#" + cte.getValue();
         }else{
            if (oper2 instanceof Variable){
                Variable var1=(Variable) oper2;
                SymbolVariable SimVar1=(SymbolVariable) var1.getAmbito().getSymbolTable().getSymbol(var1.getName());
                if ( SimVar1.getScope().getName().equals(var1.getScope().getName()) ){ 
                   operador2 = "#-" + SimVar1.getDesplazamiento() + "["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar1.getScope().getLevel()+" , "+REGISTERS[9] +" \n";
                   trad=trad+"SUB "+REGISTERS[9] +" , #"+SimVar1.getDesplazamiento()+"\n";
                   trad=trad+"MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                   operador2 = ""+REGISTERS[10] +"";
                }
            }else{
                Temporal temp = (Temporal) oper2; 
                operador2="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
            }
        }
        // Primer OPERANDO: variable cjto
        Variable var = (Variable) oper1;
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        
        if (SimVar.getScope().getName().equals(var.getScope().getName())) {
            int d = SimVar.getDesplazamiento()-1;
            trad = trad + "ADD "+operador2+" ,#" + d +"\n";
            trad = trad + "SUB "+REGISTERS[4]+" , "+REGISTERS[5]+" \n";
            operador1 = "["+REGISTERS[5]+"]";
        } else {
            // Variable en otro ambito 
            trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
            trad = trad + "SUB "+REGISTERS[7] +" , #" + SimVar.getDesplazamiento() + "\n";
            trad = trad + "ADD "+operador2+" , ["+REGISTERS[5]+"] \n";
            trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
            operador1 = ""+REGISTERS[8] +"";
        }
        
        // Resultado
        Temporal temp = (Temporal) rdo; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
        
        trad = trad + "MOVE "+operador1+" , " + resultado ;
        
        return trad;
    }
    private String traducir_UNION_SET(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo   = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        OperandIF oper2 = quadruple.getSecondOperand();
        String operador1="";
        String operador2="";
        String resultado="";
        
        // Resultado
        Temporal temp = (Temporal) rdo; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
        
        int d = 0;
        
        for (int i=0; i<temp.getSize(); i++){
            // Oper1 es variable o temporal
            if (oper1 instanceof Variable) {
                Variable var = (Variable) oper1;
                SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                
                d = SimVar.getDesplazamiento()+i;
                if (SimVar.getScope().getName().equals(var.getScope().getName())) {
                    operador1 = "#-" + d + "["+REGISTERS[4]+"]";
                } else {
                    // Variable en otro ambito 
                    trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
                    trad = trad + "SUB "+REGISTERS[7] +" , #" + d + "\n";
                    trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                    operador1 = ""+REGISTERS[8] +"";
                }
            } else {
                Temporal temp1 = (Temporal) oper1;
                d = temp1.getDesplazamiento()+i;
                operador1 = "#-" + d + "["+REGISTERS[4]+"]";
            }
            
            // Oper1 es variable o temporal
            if (oper2 instanceof Variable) {
                Variable var2 = (Variable) oper2;
                SymbolVariable SimVar2 = (SymbolVariable) var2.getAmbito().getSymbolTable().getSymbol(var2.getName());
                d = SimVar2.getDesplazamiento()+i;
                if (SimVar2.getScope().getName().equals(var2.getScope().getName())) {
                    operador2 = "#-" + d + "["+REGISTERS[4]+"]";
                } else {    
                    // Variable en otro ambito 
                    trad = trad + "MOVE /" + SimVar2.getScope().getLevel() + " , "+REGISTERS[9] +" \n";
                    trad = trad + "SUB "+REGISTERS[9] +" , #" + d + "\n";
                    trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[10] +" \n";
                    operador2 = ""+REGISTERS[10] +"";
                }
            } else {
                Temporal temp2 = (Temporal) oper2;
                d = temp2.getDesplazamiento()+i;
                operador2 = "#-" + d + "["+REGISTERS[4]+"]";
            }
            
            d = temp.getDesplazamiento() + i;
            resultado="#-" + d + "["+REGISTERS[4]+"]";
            trad = trad + "OR "+operador1+" , "+operador2+"\n";
            trad = trad + "MOVE "+REGISTERS[5]+" , "+resultado+"\n";
            
        }
        return trad;
    }
    private String traducir_ASIG_SET(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        OperandIF rdo   = quadruple.getResult();
        OperandIF oper1 = quadruple.getFirstOperand();
        String operador1="";
        String resultado="";
        
        // Operando 1: Variable cjto
        Variable var = (Variable) rdo;
        SymbolVariable SimVar = (SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
        TypeArray tipoArray = (TypeArray) SimVar.getType();
        // Resultado
        Temporal temp = (Temporal) oper1; 
        resultado="#-" + temp.getDesplazamiento() + "["+REGISTERS[4]+"]";
        
        int d = 0;
        
        for (int i=0; i<tipoArray.getSize(); i++){
            d = SimVar.getDesplazamiento()+i;
            if (SimVar.getScope().getName().equals(var.getScope().getName())) {
                operador1 = "#-" + d + "["+REGISTERS[4]+"]";
            } else {
                // Variable en otro ambito 
                trad = trad + "MOVE /" + SimVar.getScope().getLevel() + " , "+REGISTERS[7] +" \n";
                trad = trad + "SUB "+REGISTERS[7] +" , #" + d + "\n";
                trad = trad + "MOVE ["+REGISTERS[5]+"] , "+REGISTERS[8] +" \n";
                operador1 = ""+REGISTERS[8] +"";
            }
            d = temp.getDesplazamiento() + i;
            resultado="#-" + d + "["+REGISTERS[4]+"]";
            trad = trad + "MOVE "+resultado+" , "+operador1+"\n";
            
        }
        return trad;
    }
    private String traducir_CALL(QuadrupleIF quadruple){
        
        String trad="; Llamada Funcion "+quadruple+"\n";
        Variable rdo = (Variable) quadruple.getResult();
        Value valor = (Value) quadruple.getFirstOperand();
        int nivel = Integer.parseInt(valor.getValue().toString());
        OperandIF oper2 = quadruple.getSecondOperand();
        
        //String etiqRet = cambiarEtiqueta(rdo.getEtiqRetorno().toString());
        //String etiq = cambiarEtiqueta(rdo.getEtiqSub().toString());
        
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
        trad=trad+"MOVE /"+nivel +" , "+RA_VINCULO_ACCESO+"\n";        
        trad=trad+"MOVE "+REGISTERS[4]+" , /"+nivel+"\n";        
        
        trad= trad+"MOVE "+REGISTERS[4]+" , "+REGISTERS[3]+"\n";
        trad= trad+"MOVE "+REGISTERS[1] +" , "+REGISTERS[4]  +"\n";
        
        // Consideramos variables y temporales
        trad=trad+"SUB "+REGISTERS[1]+" , #" + despl+"\n";  
        trad=trad+"MOVE "+REGISTERS[5]+" , "+REGISTERS[1]+"\n";
        trad=trad+"MOVE #RET_"+ etiqRet  +" , "+RA_DIRECCION_RETORNO+"\n";     
        trad=trad+"MOVE "+REGISTERS[3]+" , "+RA_VINCULO_CONTROL+"\n";        
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
            trad=trad+"NOP \n";    
        }    
        contadorLlamadas--;
        return trad;      
        
    }
     private String traducir_RETORNO(QuadrupleIF quadruple){
        String trad= "; Traducir "+quadruple.toString() +"\n";
        String operador1= "";
        OperandIF rdo = quadruple.getResult();
        
        OperandIF oper1 = quadruple.getFirstOperand();
        Variable resultado = (Variable) rdo;
        //String etiqRetorno = cambiarEtiqueta(resultado.getEtiqRetorno().getName());
        String etiqRetorno = resultado.getEtiqRetorno().getName();
        
        
        if (oper1 instanceof Value){
            Value cte = (Value) oper1;
            operador1 = "#" + cte.getValue();
        }else{
            if (oper1 instanceof Variable) {
                Variable var = (Variable) oper1;
                SymbolVariable SimVar=(SymbolVariable) var.getAmbito().getSymbolTable().getSymbol(var.getName());
                if ( SimVar.getScope().getName().equals(var.getScope().getName()) ){ 
                   operador1 = "#-"+SimVar.getDesplazamiento()+"["+REGISTERS[4]+"]";
                }else {
                    
                   // Variable en otro ambito 
                   trad=trad+"MOVE /"+SimVar.getScope().getLevel()+" , "+REGISTERS[7] +" \n";
                   trad=trad+"SUB "+REGISTERS[7] +" , #"+SimVar.getDesplazamiento()+"\n";
                   trad=trad+"MOVE [REGISTERS[5]] , "+REGISTERS[8] +" \n";
                   operador1 = ""+REGISTERS[8] +"";
                }
           }else{
                Temporal temp = (Temporal) oper1;
                operador1= "#-"+temp.getDesplazamiento()+"["+REGISTERS[4]+"]";
           }
        }
        trad = "MOVE "+operador1 +" , "+RA_VALOR_RETORNO +"\n";
        trad = trad +"MOVE #"+ etiqRetorno +" , "+REGISTERS[0]+"\n";      
        return trad;      
     }
 
    private String traducir_INICIO_SUBPROG(QuadrupleIF quadruple){
        String trad=";-- Definicion FUNCION/PROCEDIMIENTO \n";
        OperandIF rdo = (OperandIF) quadruple.getResult();        
        
        //String etiq = cambiarEtiqueta(rdo.toString());
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
        
        //String etiqFin = cambiarEtiqueta(oper1.toString());
        String etiqFin = oper1.toString();
        trad=trad+etiqFin+" : \n";
        // Pasar valores por Referencia (Copia - valor)
        trad=trad+"; Retorno Argumentos REFERENCIA \n";
        trad=trad+"MOVE #-4["+REGISTERS[4]+"] , "+REGISTERS[11] +" \n"; 
        trad=trad+"MOVE "+REGISTERS[11] +" , "+REGISTERS[0]+" \n";
        //trad=trad+"REF_"+cambiarEtiqueta(rdo.toString())+": \n";
        trad=trad+"REF_"+rdo.toString()+": \n";
                
        trad=trad+"; Retorno Subprograma \n";
        trad=trad+"MOVE "+RA_VALOR_RETORNO+" , "+REGISTERS[15] +""+"\n"; // se queda el valor de retorno en el R9
        trad=trad+"MOVE "+RA_DIRECCION_RETORNO+" , .R7\n";
        
        // Arreglo ambitos
        trad=trad+"MOVE "+RA_VINCULO_ACCESO+" , /"+nivel+"\n";
        
        trad=trad+"MOVE "+REGISTERS[4]+" , "+REGISTERS[1]+"\n";
        trad=trad+"MOVE "+REGISTERS[3]+" , "+REGISTERS[4]+"\n";
        trad=trad+"MOVE "+RA_VINCULO_CONTROL +", "+REGISTERS[3]+"\n";        
        trad=trad+"MOVE .R7 , "+REGISTERS[0]+"\n";
        
        //trad=trad+"FIN_"+cambiarEtiqueta(rdo.toString())+" : \n";
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
        // parPorReferencia.put(var.getAmbito().getLevel(), listaPorReferencia);
            
        return "; Cargado argumento REFERENCIA "+quadruple;
        
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
                 resultado = "#-" + SimVar.getDesplazamiento() + "["+REGISTERS[4]+"]";
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
                 resultado = "#-" + desp + "["+REGISTERS[4]+"]";
            }
       }
       trad= trad+"WRINT "+resultado;
       trad = trad + "\n; Escribimos un salto de linea\n";
       trad = trad + "WRSTR /cadena0";
       return trad;

    }
    
    // Escribir una cadena
     private String traducir_WRSTR(QuadrupleIF quadruple) {
    	 String trad = "";
         String operador = quadruple.getResult().toString();
         OperandIF o = quadruple.getFirstOperand();
         trad= "WRSTR /" + operador + "\nWRCHAR #10\nWRCHAR #13";
         
         //trad = trad + "\n; Escribimos un salto de linea\n";
         //trad = trad + "WRSTR /cadena0";
         
         return trad;

    }

     
/*     private String traducir_INL(QuadrupleIF quadruple) {
         String trad = "";
         trad = "; Traducir INL: Salto a una etiqueta\n";
         String etiq = eliminaPuntos(quadruple.getResult().toString());
         trad = "; Definimos la posicion de la etiqueta " + etiq;
         trad = trad + "\n\t" + etiq + " :";
         return trad;
     }
*/     
     private String eliminaPuntos(String cadena) {
         return cadena.replace(".", "");
     }
     
     
 /*   private String traducir_WRTLN(QuadrupleIF quadruple) {
        String trad = "";
        trad = "; Escribimos un salto de linea\n";
        trad = trad + "WRSTR /cadena0";
        return trad;
    }*/
	
/*    private String cambiarEtiqueta(String etiq){
       return etiq.replace("_", "");
   }*/
       


}
