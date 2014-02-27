package compiler.semantic.symbol;

import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolVariable.
 */

// TODO: Student work
//       Include properties to characterize parameters

public class SymbolParameter extends SymbolBase implements SymbolAddressIF
{  
        TemporalIF temporal;
        int address = 0;
/**
         * @return the temporal
         */
        public TemporalIF getTemporal() {
                return temporal;
        }

        /**
         * @param temporal the temporal to set
         */
        public void setTemporal(TemporalIF temporal) {
                this.temporal = temporal;
        }

        //      private Object value;
    /**
     * Constructor for SymbolParameter.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolParameter (ScopeIF scope, 
                           String name,
                           TypeIF type)
    {
        super (scope, name, type);
    }
//      public void setValue(Object value) {
//              this.value = value;
//      }
//      public Object getValue() {
//              return value;
//      } 

        @Override
        public int getAddress() {
                return address;
        }

        @Override
        public void setAddress(int address) {
                this.address = address;         
        }
}