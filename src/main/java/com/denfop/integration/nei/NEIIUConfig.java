
package com.denfop.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIIUConfig implements IConfigureNEI {
	public void loadConfig() {
		System.out.println("[Super Solar Panels]: Loading NEI compatibility...");
		API.registerUsageHandler(new NEIRolling());
		API.registerRecipeHandler(new NEIRolling());
		API.registerUsageHandler(new NEIExtruding());
		API.registerRecipeHandler(new NEIExtruding());
		API.registerUsageHandler(new NEICutting());
		API.registerRecipeHandler(new NEICutting());
		API.registerUsageHandler(new NEIPainting());
		API.registerRecipeHandler(new NEIPainting());
		API.registerUsageHandler(new NEIUpgradeBlock());
		API.registerRecipeHandler(new NEIUpgradeBlock());
		API.registerUsageHandler(new NEIConverterMatter());
		API.registerRecipeHandler(new NEIConverterMatter());
		API.registerUsageHandler(new NEIDoubleMolecular());
		API.registerRecipeHandler(new NEIDoubleMolecular());

		API.registerUsageHandler(new NEICombMacerator());
		API.registerRecipeHandler(new NEICombMacerator());
		API.registerUsageHandler(new NEIFermer());
		API.registerRecipeHandler(new NEIFermer());
		API.registerUsageHandler(new NEIAsmSca());
		API.registerRecipeHandler(new NEIAsmSca());
		API.registerUsageHandler(new NEIWitherMaker());
		API.registerRecipeHandler(new NEIWitherMaker());
		API.registerRecipeHandler(new NEIAlloySmelter());
		API.registerUsageHandler(new NEIAlloySmelter());
		API.registerRecipeHandler(new NEIAdvAlloySmelter());
		API.registerUsageHandler(new NEIAdvAlloySmelter());

		API.registerRecipeHandler(new NeiMolecularTransfomator());
		API.registerUsageHandler(new NeiMolecularTransfomator());
		API.registerRecipeHandler(new NEIGPU());
		API.registerUsageHandler(new NEIGPU());
		API.registerUsageHandler(new NEISunnarium());
		API.registerRecipeHandler(new NEISunnarium());
		API.registerUsageHandler(new NeiSynthesis());
		API.registerRecipeHandler(new NeiSynthesis());
		API.registerUsageHandler(new NeiEnrich());
		API.registerRecipeHandler(new NeiEnrich());
		API.registerUsageHandler(new NEISunnariumPanel());
		API.registerRecipeHandler(new NEISunnariumPanel());
		API.registerUsageHandler(new NEIHandlerHO());
		API.registerRecipeHandler(new NEIHandlerHO());


	}

	public String getName() {
		return "Super Solar Panels";
	}

	public String getVersion() {
		return "v1.0";
	}
}
