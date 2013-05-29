package husacct.define.domain.module.ToBeImplemented.modules;




import java.util.ArrayList;

import husacct.define.domain.SoftwareUnitDefinition;
import husacct.define.domain.SoftwareUnitRegExDefinition;
import husacct.define.domain.module.ToBeImplemented.ModuleFactory;
import husacct.define.domain.module.ToBeImplemented.ModuleStrategy;





public class Component extends ModuleStrategy {

	
	public void set(String name, String description){
		this.id = STATIC_ID;
		STATIC_ID++;
		this.name = name;
		this.description = description;
		this.type = "Module";
		this.mappedSUunits = new ArrayList<SoftwareUnitDefinition>();
		this.mappedRegExSUunits = new ArrayList<SoftwareUnitRegExDefinition>();
		this.subModules = new ArrayList<ModuleStrategy>();
	    ModuleStrategy facade = new ModuleFactory().createModule("Facade");
	    facade.set(name+"Facade","this is the Facade of your Component");
	    this.subModules.add(facade);
	}
	
}
