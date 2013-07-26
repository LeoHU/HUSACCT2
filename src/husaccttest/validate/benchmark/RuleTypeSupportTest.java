package husaccttest.validate.benchmark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import husacct.common.dto.ApplicationDTO;
import husacct.common.dto.ModuleDTO;
import husacct.common.dto.ProjectDTO;
import husacct.common.dto.RuleDTO;
import husacct.define.domain.appliedrule.AppliedRuleStrategy;
import husacct.define.domain.module.ModuleStrategy;
import husacct.validate.domain.factory.message.Messagebuilder;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.ruletype.RuleTypes;
import husaccttest.validate.benchmark.testfiles.ViolationsRuleXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RuleTypeSupportTest {
	private static RuleTypeSupport srmaTest;
	private static Messagebuilder violationMessageBuilder;
	private static Logger logger;
	
	@BeforeClass
	public static void beforeClass() {
		setLog4jConfiguration();
		
		violationMessageBuilder = new Messagebuilder();
		
		srmaTest = new RuleTypeSupport();
		srmaTest.setUpSRMATest();
	}
	
	@AfterClass
	public static void tearDown(){
		
	}
	
	@Test
	public void createApplication() {		
		ApplicationDTO applicationDTO = srmaTest.getDefineService().getApplicationDetails();
		ProjectDTO projectDTO = applicationDTO.projects.get(0);
		
		assertEquals(applicationDTO.name, srmaTest.getApplicationName());
		assertEquals(projectDTO.paths.get(0), srmaTest.getProjectSourcePath());
		assertEquals(projectDTO.programmingLanguage, srmaTest.getProgramLanguage());
		assertEquals(applicationDTO.version, srmaTest.getProgramVersion());
	}
	
	@Test
	public void analyseApplicationSucceeded() {
		assertTrue(srmaTest.getAnalyseService().isAnalysed());
	}
	
	@Test
	public void analyseApplicationDetection() {
		assertNotSame(0, srmaTest.getAnalyseService().getAmountOfDependencies());
		assertNotSame(0, srmaTest.getAnalyseService().getAmountOfPackages());
		assertNotSame(0, srmaTest.getAnalyseService().getAmountOfClasses());
		assertNotSame(0, srmaTest.getAnalyseService().getAmountOfInterfaces());
	}
	
	@Test
	public void getAmountStoredModulesSA() {
		ModuleDTO[] storedModules = srmaTest.getDefineService().getRootModules();
		ArrayList<ModuleStrategy> definedModules = srmaTest.getSoftwareArchitecture().getModules();
		
		assertEquals(storedModules.length, definedModules.size());
	}
	
	@Test
	public void getAmountStoredAppliedRules() {
		RuleDTO[] storedRuleTypes = srmaTest.getDefineService().getDefinedRules();
		ArrayList<AppliedRuleStrategy> definedRuleTypes = srmaTest.getSoftwareArchitecture().getAppliedRules();
		
		assertEquals(storedRuleTypes.length, definedRuleTypes.size());
	}
	
	@Test
	public void getAmountGeneratedViolations() {
		ArrayList<Violation> allViolations = srmaTest.getAllViolations();
		
		assertNotSame(0, allViolations.size());
	}

	@Test
	public void ruleType1_BackCall() {
		assertTrue(checkOverallOperationRuleType(1, RuleTypes.IS_NOT_ALLOWED_BACK_CALL));
	}
	
	@Test
	public void ruleType2_BackCall() {
		assertTrue(checkOverallOperationRuleType(2, RuleTypes.IS_NOT_ALLOWED_BACK_CALL));
	}	
	
	@Test
	public void ruleType3_BackCall() {
		assertTrue(checkOverallOperationRuleType(3, RuleTypes.IS_NOT_ALLOWED_BACK_CALL));
	}
	
	@Test
	public void ruleType4_SkipCall() {
		assertTrue(checkOverallOperationRuleType(4, RuleTypes.IS_NOT_ALLOWED_SKIP_CALL));
	}
	
	@Test
	public void ruleType5_NamingConvention_prefix() {
		assertTrue(checkOverallOperationRuleType(5, RuleTypes.NAMING_CONVENTION));
	}
	
	@Test
	public void ruleType6_NamingConvention_postfix() {
		assertTrue(checkOverallOperationRuleType(6, RuleTypes.NAMING_CONVENTION));
	}
	
	@Test
	public void ruleType7_NamingConvention_mid() {
		assertTrue(checkOverallOperationRuleType(7, RuleTypes.NAMING_CONVENTION));
	}
	
	@Test
	public void ruleType8_VisibilityConvention() {
		assertTrue(checkOverallOperationRuleType(8, RuleTypes.VISIBILITY_CONVENTION));
	}
	
	@Test
	public void ruleType9_FacadeConvention() {
		assertTrue(checkOverallOperationRuleType(9, RuleTypes.FACADE_CONVENTION));
	}
	
	@Test
	public void ruleType10_SuperClassInheritanceConvention() {
		assertTrue(checkOverallOperationRuleType(10, RuleTypes.SUPERCLASSINHERITANCE_CONVENTION));
	}
	
	@Test
	public void ruleType11_InterfaceInheritanceConvention() {
		assertTrue(checkOverallOperationRuleType(11, RuleTypes.INTERFACEINHERITANCE_CONVENTION));
	}
	
	@Test
	public void ruleType12_IsAllowedToUse() {
		assertTrue(checkOverallOperationRuleType(12, RuleTypes.IS_ALLOWED_TO_USE));
	}
	
	@Test
	public void ruleType13_IsOnlyAllowedToUse() {
		assertTrue(checkOverallOperationRuleType(13, RuleTypes.IS_ONLY_ALLOWED_TO_USE));
	}
	
	@Test
	public void ruleType14_MustUse_Correct() {
		assertTrue(checkOverallOperationRuleType(14, RuleTypes.MUST_USE));
	}
	
	@Test
	public void ruleType15_MustUse_Violating() {
		assertTrue(checkOverallOperationRuleType(15, RuleTypes.MUST_USE));
	}
	
	@Test
	public void ruleType16_IsTheOnlyModuleAllowedToUse() {
		assertTrue(checkOverallOperationRuleType(16, RuleTypes.IS_THE_ONLY_MODULE_ALLOWED_TO_USE));
	}
	
	@Test
	public void ruleType17_IsNotAllowedToUse() {
		assertTrue(checkOverallOperationRuleType(17, RuleTypes.IS_NOT_ALLOWED_TO_USE));
	}
	
	@Test
	public void ruleType18_IsNotAllowedToUse_ExternalSystem() {
		assertTrue(checkOverallOperationRuleType(18, RuleTypes.IS_NOT_ALLOWED_TO_USE));
	}
	
	
	//OTHER METHODS
	private static void setLog4jConfiguration() {
		URL propertiesFile = Class.class.getResource("/husacct/common/resources/log4j.properties");
		PropertyConfigurator.configure(propertiesFile);
		logger = Logger.getLogger(RuleTypeSupportTest.class);
	}
	
	private boolean checkOverallOperationRuleType(int ruleTypeNumber, RuleTypes ruleType) {
		ArrayList<Violation> xmlViolations = giveRelatedViolationsFromXMLFile(ruleTypeNumber);
		TreeSet<String> classPathsFrom = new TreeSet<String>();
		TreeSet<String> classPathsTo = new TreeSet<String>();
		for(Violation xmlViolation: xmlViolations) {
			classPathsFrom.add(xmlViolation.getClassPathFrom());
			classPathsTo.add(xmlViolation.getClassPathTo());
		}
		ArrayList<Violation> generatedViolations = giveRelatedViolationsFromExecutedTest(ruleType, classPathsFrom, classPathsTo);
		
		return checkConformityDifferentViolationSources(xmlViolations, generatedViolations);
	}
	
	private boolean checkConformityDifferentViolationSources(ArrayList<Violation> xmlViolations, ArrayList<Violation> generatedViolations) {
		if(xmlViolations.size() == generatedViolations.size()) {
			for(int counter = 0; counter < xmlViolations.size(); counter++) {
				Violation xmlViolation = xmlViolations.get(counter);
				Violation generatedViolation = generatedViolations.get(counter);
				
				if(checkConformityTwoViolations(xmlViolation, generatedViolation)) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean checkConformityTwoViolations(Violation violation1, Violation violation2) {	
		if(violation1.getClassPathFrom().toLowerCase().equals(violation2.getClassPathFrom().toLowerCase())) {
			if(violation1.getClassPathTo().toLowerCase().equals(violation2.getClassPathTo().toLowerCase())) {
				if(violation1.getLinenumber() == violation2.getLinenumber()) {
					if(violation1.getViolationTypeKey().toLowerCase().equals(violation2.getViolationTypeKey().toLowerCase())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private ArrayList<Violation> giveRelatedViolationsFromXMLFile(int fileNumber) {
		ViolationsRuleXML violationRuleFromXML = new ViolationsRuleXML();
		
		ArrayList<Violation> foundedViolations = violationRuleFromXML.giveViolationsOutXMLFile(fileNumber);
		return foundedViolations;
	}
		
	private ArrayList<Violation> giveRelatedViolationsFromExecutedTest(RuleTypes ruleType, TreeSet<String> classPathsFrom, TreeSet<String> classPathsTo) {
		//RuleTypes.>
		ArrayList<Violation> generatedViolations = new ArrayList<Violation>();
		for(Violation theViolation: srmaTest.getAllViolations()) {
			if(theViolation.getRuletypeKey().toLowerCase().equals(ruleType.name().toLowerCase())) {
				for(String classPathFrom: classPathsFrom) {
					if(theViolation.getClassPathFrom().toLowerCase().equals(classPathFrom)) {
						for(String classPathTo: classPathsTo) {
							if(theViolation.getClassPathTo().toLowerCase().equals(classPathTo)) {
								generatedViolations.add(theViolation);
							}
						}
					}
				}
			}
		}
		
		return generatedViolations;
	}
	
	private void giveReviewGeneratedViolations() {
		int counter = 0;
		Messagebuilder testBuilder;
		for(Violation theViolation: srmaTest.getAllViolations()) {
			counter++;
			testBuilder = new Messagebuilder();
			
			System.out.println(testBuilder.createMessage(theViolation));
			
			
			StringBuilder violationString = new StringBuilder();
			violationString.append(counter);
			violationString.append(" > ");
			violationString.append(theViolation.getViolationTypeKey());
			violationString.append(" - ");
			violationString.append(theViolation.getRuletypeKey());
			violationString.append(" - ");
			violationString.append(theViolation.getLinenumber());
			violationString.append(" - ");
			violationString.append(theViolation.getClassPathFrom());
			violationString.append(" - ");
			violationString.append(theViolation.getClassPathTo());
			
			System.err.println(violationString.toString());
		}
	}
}