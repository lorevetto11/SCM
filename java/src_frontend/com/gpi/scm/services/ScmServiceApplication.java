package com.gpi.scm.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import com.gpi.scm.filters.BearerTokenFilter;
import com.gpi.scm.filters.RefreshTokenFilter;

@ApplicationPath("/services")
public class ScmServiceApplication extends Application {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<Object> singletons = new HashSet();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<Class<?>> empty = new HashSet();

	public ScmServiceApplication() throws JoseException, InvalidJwtException {

		this.singletons.add(new ProcedureService());
		this.singletons.add(new FrequencyService());
		this.singletons.add(new MonitoringService());
		this.singletons.add(new UserService());
		this.singletons.add(new UserRoleService());
		this.singletons.add(new UserProfileService());
		this.singletons.add(new OrganizationService());
		this.singletons.add(new AttachmentService());
		this.singletons.add(new FloorService());
		this.singletons.add(new RiskClassService());
		this.singletons.add(new LayoutService());
		this.singletons.add(new WasteDisposalService());
		this.singletons.add(new WaterSupplyService());
		this.singletons.add(new AirConditioningService());
		this.singletons.add(new CleaningService());
		this.singletons.add(new PestControlService());
		this.singletons.add(new EquipmentPrerequisiteService());
		this.singletons.add(new GrantService());
		this.singletons.add(new OutcomeService());
		this.singletons.add(new ShapeService());
		this.singletons.add(new CourseService());
		this.singletons.add(new TrainingService());
		this.singletons.add(new ParticipantService());
		this.singletons.add(new PrerequisiteTypeService());
		this.singletons.add(new OrganizationPlaceService());
		this.singletons.add(new OrganizationCertificationService());
		this.singletons.add(new EquipmentTypeService ());
		this.singletons.add(new EquipmentService ());
		this.singletons.add(new SystemCheckService());
		this.singletons.add(new SystemCheckPlanningService());
		this.singletons.add(new SystemCheckRequirementService());
		this.singletons.add(new SystemCheckOutcomeService());
		this.singletons.add(new ProcessCheckService());
		this.singletons.add(new ProcessCheckPlanningService());
		this.singletons.add(new ProcessCheckOutcomeService());
		this.singletons.add(new NonComplianceService());
		this.singletons.add(new DangerService());
		this.singletons.add(new StaffHygieneService());
		this.singletons.add(new AnalysisParameterService());
		this.singletons.add(new AnalysisValueService());
		this.singletons.add(new WaterSupplyTypeService());
		this.singletons.add(new AirConditioningTypeService());
		this.singletons.add(new PestControlTypeService());
		this.singletons.add(new WasteDisposalTypeService());
		this.singletons.add(new SupplierService());
		this.singletons.add(new MaterialService());
		this.singletons.add(new MaterialCategoryService());
		this.singletons.add(new DiagramService());
		this.singletons.add(new FlowShapeService());
		this.singletons.add(new FlowAnchorPointService());
		this.singletons.add(new FlowElementService());
		this.singletons.add(new FlowRelationService());
		this.singletons.add(new RiskMapService());


	}

	@Override
	public Set<Class<?>> getClasses() {
		empty.add(BearerTokenFilter.class);
		empty.add(RefreshTokenFilter.class);
		empty.add(WSExceptionHandler.class);
		return this.empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}

}
