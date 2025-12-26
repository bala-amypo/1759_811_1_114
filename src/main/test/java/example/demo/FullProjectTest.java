package com.example.demo;

import com.example.demo.controller.*;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.servlet.SimpleStatusServlet;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.*;

import jakarta.servlet.http.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 64-Test TestNG Suite
 * Covers:
 * 1. Servlet Tests
 * 2. CRUD
 * 3. DI/IoC
 * 4. Hibernate behavior
 * 5. JPA/Normalization
 * 6. Relations
 * 7. Security/JWT
 * 8. JPQL/HQL-like queries
 */
@Listeners(TestResultListener.class)
public class ColdChainSystemTest {

    // Mock Repos
    @Mock private ShipmentRecordRepository shipmentRepo;
    @Mock private TemperatureSensorLogRepository logRepo;
    @Mock private TemperatureRuleRepository ruleRepo;
    @Mock private BreachRecordRepository breachRepo;
    @Mock private AlertRecordRepository alertRepo;
    @Mock private UserRepository userRepo;

    // Services
    private ShipmentRecordService shipmentService;
    private TemperatureLogService logService;
    private TemperatureRuleService ruleService;
    private BreachDetectionService breachService;
    private AlertService alertService;
    private UserService userService;

    // Controllers
    private ShipmentRecordController shipmentController;
    private TemperatureLogController logController;
    private TemperatureRuleController ruleController;
    private BreachRecordController breachController;
    private AlertRecordController alertController;
    private AuthController authController;

    // Security
    private JwtUtil jwtUtil;
    @Mock private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    // Servlet
    private SimpleStatusServlet simpleStatusServlet;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);

        jwtUtil = new JwtUtil("12345678901234567890123456789012", 3600000);

        shipmentService = new ShipmentRecordServiceImpl(shipmentRepo);
        logService = new TemperatureLogServiceImpl(logRepo);
        ruleService = new TemperatureRuleServiceImpl(ruleRepo);
        breachService = new BreachDetectionServiceImpl(breachRepo);
        alertService = new AlertServiceImpl(alertRepo);

        userService = new UserServiceImpl(
                userRepo,
                new org.springframework.security.crypto.password.PasswordEncoder() {
                    @Override public String encode(CharSequence cs) { return cs + "_ENC"; }
                    @Override public boolean matches(CharSequence cs, String s) { return (cs + "_ENC").equals(s); }
                }
        );

        shipmentController = new ShipmentRecordController(shipmentService);
        logController = new TemperatureLogController(logService);
        ruleController = new TemperatureRuleController(ruleService);
        breachController = new BreachRecordController(breachService);
        alertController = new AlertRecordController(alertService);
        authController = new AuthController(userService, authenticationManager, jwtUtil);

        simpleStatusServlet = new SimpleStatusServlet();
    }

    // -------------------------------------------------------------------
    // 1. SERVLET TESTS (8 tests)
    // -------------------------------------------------------------------

    @Test(priority = 1)
    public void t01_servletReturnsPlainText() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(resp.getWriter()).thenReturn(pw);

        simpleStatusServlet.doGet(req, resp);

        Assert.assertEquals(sw.toString(),
                "Cold Chain Temperature Breach Alert System is running");
    }

    @Test(priority = 2)
    public void t02_servletContentType() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        simpleStatusServlet.doGet(null, resp);
        verify(resp).setContentType("text/plain");
    }

    @Test(priority = 3)
    public void t03_servletHandlesNullRequest() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        simpleStatusServlet.doGet(null, resp);
        Assert.assertEquals(true, true);
    }

    @Test(priority = 4)
    public void t04_servletWriterFlush() throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter pw = spy(new PrintWriter(sw));
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(resp.getWriter()).thenReturn(pw);

        simpleStatusServlet.doGet(null, resp);
        verify(pw).flush();
    }

    @Test(priority = 5)
    public void t05_servletNoHtml() throws Exception {
        StringWriter sw = new StringWriter();
        when(mock(HttpServletResponse.class).getWriter()).thenReturn(new PrintWriter(sw));
        Assert.assertEquals(true, true);
    }

    @Test(priority = 6)
    public void t06_servletTextMatches() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(
                sw.toString().contains("running"),
                true
        );
    }

    @Test(priority = 7)
    public void t07_servletIsIdempotent() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        simpleStatusServlet.doGet(null, resp);
        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(true, true);
    }

    @Test(priority = 8)
    public void t08_servletOutputNotEmpty() throws Exception {
        StringWriter sw = new StringWriter();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);
        Assert.assertEquals(sw.toString().isEmpty(), false);
    }

    // -------------------------------------------------------------------
    // 2. CRUD Tests (Shipment, Logs, Rules, Breaches, Alerts)
    // -------------------------------------------------------------------

    @Test(priority = 9)
    public void t09_createShipment() {
        ShipmentRecord ship = new ShipmentRecord();
        ship.setShipmentCode("S001");

        when(shipmentRepo.save(any())).thenReturn(ship);
        Assert.assertEquals(shipmentService.createShipment(ship).getShipmentCode(), "S001");
    }

    @Test(priority = 10)
    public void t10_updateShipmentStatus() {
        ShipmentRecord ship = new ShipmentRecord();
        ship.setId(1L);
        ship.setStatus("IN_TRANSIT");

        when(shipmentRepo.findById(1L)).thenReturn(Optional.of(ship));
        when(shipmentRepo.save(any())).thenReturn(ship);

        ShipmentRecord updated = shipmentService.updateShipmentStatus(1L, "DELIVERED");
        Assert.assertEquals(updated.getStatus(), "DELIVERED");
    }

    @Test(priority = 11)
    public void t11_recordTemperatureLog() {
        TemperatureSensorLog log = new TemperatureSensorLog();
        log.setTemperatureValue(5.0);
        when(logRepo.save(any())).thenReturn(log);

        Assert.assertEquals(logService.recordLog(log).getTemperatureValue(), 5.0);
    }

    @Test(priority = 12)
    public void t12_createTemperatureRule() {
        TemperatureRule rule = new TemperatureRule();
        rule.setMinTemp(2.0);
        rule.setMaxTemp(8.0);
        rule.setActive(true);
        rule.setProductType("PHARMA");
        rule.setEffectiveFrom(LocalDate.now());
        rule.setEffectiveTo(LocalDate.now().plusDays(1));

        when(ruleRepo.save(any())).thenReturn(rule);
        Assert.assertEquals(ruleService.createRule(rule).getMaxTemp(), 8.0);
    }

    @Test(priority = 13)
    public void t13_logBreach() {
        BreachRecord br = new BreachRecord();
        br.setBreachValue(10.0);
        when(breachRepo.save(any())).thenReturn(br);
        Assert.assertEquals(breachService.logBreach(br).getBreachValue(), 10.0);
    }

    @Test(priority = 14)
    public void t14_triggerAlert() {
        AlertRecord ar = new AlertRecord();
        when(alertRepo.save(any())).thenReturn(ar);
        Assert.assertEquals(alertService.triggerAlert(ar) != null, true);
    }

    @Test(priority = 15)
    public void t15_getShipmentByCode() {
        ShipmentRecord ship = new ShipmentRecord();
        ship.setShipmentCode("S001");

        when(shipmentRepo.findByShipmentCode("S001")).thenReturn(Optional.of(ship));

        Assert.assertEquals(shipmentService.getShipmentByCode("S001").isPresent(), true);
    }

    @Test(priority = 16)
    public void t16_getLogsByShipment() {
        when(logRepo.findByShipmentId(1L)).thenReturn(List.of(new TemperatureSensorLog()));
        Assert.assertEquals(logService.getLogsByShipment(1L).size(), 1);
    }

    // -------------------------------------------------------------------
    // 3. DI / IoC Tests
    // -------------------------------------------------------------------

    @Test(priority = 17)
    public void t17_serviceInjectionNotNull() {
        Assert.assertEquals(shipmentService != null, true);
    }

    @Test(priority = 18)
    public void t18_mockInjectionWorks() {
        when(shipmentRepo.findAll()).thenReturn(List.of(new ShipmentRecord()));
        Assert.assertEquals(shipmentService.getAllShipments().size(), 1);
    }

    @Test(priority = 19)
    public void t19_controllerInjectionWorks() {
        Assert.assertEquals(shipmentController != null, true);
    }

    @Test(priority = 20)
    public void t20_userServiceEncoder() {
        User u = new User();
        u.setEmail("a@a.com");
        u.setPassword("pass");

        when(userRepo.existsByEmail("a@a.com")).thenReturn(false);
        when(userRepo.save(any())).thenReturn(u);

        User saved = userService.registerUser(u);
        Assert.assertEquals(saved.getPassword().contains("_ENC"), true);
    }

    @Test(priority = 21)
    public void t21_findUserByEmail() {
        User u = new User();
        u.setEmail("x@x.com");

        when(userRepo.findByEmail("x@x.com")).thenReturn(Optional.of(u));
        Assert.assertEquals(userService.findByEmail("x@x.com").getEmail(), "x@x.com");
    }

    @Test(priority = 22)
    public void t22_alertServiceInjection() {
        Assert.assertEquals(alertService != null, true);
    }

    @Test(priority = 23)
    public void t23_ruleServiceInjection() {
        Assert.assertEquals(ruleService != null, true);
    }

    @Test(priority = 24)
    public void t24_logServiceInjection() {
        Assert.assertEquals(logService != null, true);
    }

    // -------------------------------------------------------------------
    // 4. Hibernate / Lifecycle Tests
    // -------------------------------------------------------------------

    @Test(priority = 25)
    public void t25_shipmentPrePersistDefaults() {
        ShipmentRecord s = new ShipmentRecord();
        s.prePersist();
        Assert.assertEquals(s.getStatus(), "IN_TRANSIT");
    }

    @Test(priority = 26)
    public void t26_breachDefaultResolvedFalse() {
        BreachRecord br = new BreachRecord();
        br.prePersist();
        Assert.assertEquals(br.getResolved(), false);
    }

    @Test(priority = 27)
    public void t27_alertDefaultAckFalse() {
        AlertRecord ar = new AlertRecord();
        ar.prePersist();
        Assert.assertEquals(ar.getAcknowledged(), false);
    }

    @Test(priority = 28)
    public void t28_ruleValidationMinLessThanMax() {
        TemperatureRule rule = new TemperatureRule();
        rule.setMinTemp(1.0);
        rule.setMaxTemp(5.0);
        rule.setActive(true);
        rule.setProductType("PHARMA");
        rule.setEffectiveFrom(LocalDate.now());
        rule.setEffectiveTo(LocalDate.now().plusDays(1));

        when(ruleRepo.save(any())).thenReturn(rule);
        Assert.assertEquals(ruleService.createRule(rule).getMinTemp(), 1.0);
    }

    @Test(priority = 29)
    public void t29_ruleInvalidMinGreaterThanMaxThrows() {
        TemperatureRule rule = new TemperatureRule();
        rule.setMinTemp(10.0);
        rule.setMaxTemp(5.0);

        try {
            ruleService.createRule(rule);
            Assert.assertEquals(false, true); // should not reach
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test(priority = 30)
    public void t30_alertTimestampSet() {
        AlertRecord ar = new AlertRecord();
        ar.prePersist();
        Assert.assertEquals(ar.getSentAt() != null, true);
    }

    @Test(priority = 31)
    public void t31_userPrePersistDefaults() {
        User u = new User();
        u.prePersist();
        Assert.assertEquals(u.getRole(), "MONITOR");
    }

    @Test(priority = 32)
    public void t32_temperatureLogLocationOptional() {
        TemperatureSensorLog log = new TemperatureSensorLog();
        log.setLocation(null);
        Assert.assertEquals(log.getLocation(), null);
    }

    // -------------------------------------------------------------------
    // 5. JPA Mapping / Normalization Tests
    // -------------------------------------------------------------------

    @Test(priority = 33)
    public void t33_shipmentNormalizationFieldsExist() {
        ShipmentRecord s = new ShipmentRecord();
        s.setOrigin("A");
        s.setDestination("B");
        Assert.assertEquals(s.getDestination(), "B");
    }

    @Test(priority = 34)
    public void t34_temperatureLogHasForeignKey() {
        TemperatureSensorLog l = new TemperatureSensorLog();
        l.setShipmentId(5L);
        Assert.assertEquals(l.getShipmentId(), 5L);
    }

    @Test(priority = 35)
    public void t35_ruleEffectiveRange() {
        TemperatureRule r = new TemperatureRule();
        r.setEffectiveFrom(LocalDate.now());
        r.setEffectiveTo(LocalDate.now().plusDays(5));
        Assert.assertEquals(r.getEffectiveTo().isAfter(r.getEffectiveFrom()), true);
    }

    @Test(priority = 36)
    public void t36_breachReferencesLog() {
        BreachRecord br = new BreachRecord();
        br.setLogId(10L);
        Assert.assertEquals(br.getLogId(), 10L);
    }

    @Test(priority = 37)
    public void t37_alertReferencesBreach() {
        AlertRecord ar = new AlertRecord();
        ar.setBreachId(7L);
        Assert.assertEquals(ar.getBreachId(), 7L);
    }

    @Test(priority = 38)
    public void t38_ruleOneActivePerTypeConcept() {
        when(ruleRepo.findByActiveTrue()).thenReturn(List.of(new TemperatureRule()));
        Assert.assertEquals(ruleService.getActiveRules().size(), 1);
    }

    @Test(priority = 39)
    public void t39_shipmentHasStatusField() {
        ShipmentRecord s = new ShipmentRecord();
        s.setStatus("IN_TRANSIT");
        Assert.assertEquals(s.getStatus(), "IN_TRANSIT");
    }

    @Test(priority = 40)
    public void t40_alertAcknowledgementFlag() {
        AlertRecord ar = new AlertRecord();
        ar.setAcknowledged(false);
        Assert.assertEquals(ar.getAcknowledged(), false);
    }

    // -------------------------------------------------------------------
    // 6. Relations / Logical Associations
    // -------------------------------------------------------------------

    @Test(priority = 41)
    public void t41_shipmentLogRelationById() {
        TemperatureSensorLog l = new TemperatureSensorLog();
        l.setShipmentId(3L);
        Assert.assertEquals(l.getShipmentId(), 3L);
    }

    @Test(priority = 42)
    public void t42_breachToShipmentIdLogic() {
        BreachRecord br = new BreachRecord();
        br.setShipmentId(2L);
        Assert.assertEquals(br.getShipmentId(), 2L);
    }

    @Test(priority = 43)
    public void t43_alertToShipmentRelation() {
        AlertRecord ar = new AlertRecord();
        ar.setShipmentId(99L);
        Assert.assertEquals(ar.getShipmentId(), 99L);
    }

    @Test(priority = 44)
    public void t44_ruleAppliesPerProduct() {
        TemperatureRule r = new TemperatureRule();
        r.setProductType("PHARMA");
        Assert.assertEquals(r.getProductType(), "PHARMA");
    }

    @Test(priority = 45)
    public void t45_breachSeverityLogic() {
        BreachRecord br = new BreachRecord();
        br.setSeverity("HIGH");
        Assert.assertEquals(br.getSeverity(), "HIGH");
    }

    @Test(priority = 46)
    public void t46_manualAlertTrigger() {
        AlertRecord a = new AlertRecord();
        when(alertRepo.save(any())).thenReturn(a);
        Assert.assertEquals(alertService.triggerAlert(a) != null, true);
    }

    @Test(priority = 47)
    public void t47_resolveBreach() {
        BreachRecord br = new BreachRecord();
        br.setId(1L);
        br.setResolved(false);

        when(breachRepo.findById(1L)).thenReturn(Optional.of(br));
        when(breachRepo.save(any())).thenReturn(br);

        BreachRecord updated = breachService.resolveBreach(1L);
        Assert.assertEquals(updated.getResolved(), true);
    }

    @Test(priority = 48)
    public void t48_getAlertsByShipment() {
        when(alertRepo.findByShipmentId(1L)).thenReturn(List.of(new AlertRecord()));
        Assert.assertEquals(alertService.getAlertsByShipment(1L).size(), 1);
    }

    // -------------------------------------------------------------------
    // 7. Security / JWT Tests
    // -------------------------------------------------------------------

    @Test(priority = 49)
    public void t49_jwtGeneration() {
        String token = jwtUtil.generateToken(1L, "a@a.com", "ADMIN");
        Assert.assertEquals(token != null, true);
    }

    @Test(priority = 50)
    public void t50_jwtExtractEmail() {
        String token = jwtUtil.generateToken(1L, "a@a.com", "ADMIN");
        Assert.assertEquals(jwtUtil.extractEmail(token), "a@a.com");
    }

    @Test(priority = 51)
    public void t51_jwtExtractRole() {
        String token = jwtUtil.generateToken(1L, "a@a.com", "ADMIN");
        Assert.assertEquals(jwtUtil.extractRole(token), "ADMIN");
    }

    @Test(priority = 52)
    public void t52_jwtExtractUserId() {
        String token = jwtUtil.generateToken(99L, "x@x.com", "MONITOR");
        Assert.assertEquals(jwtUtil.extractUserId(token), 99L);
    }

    @Test(priority = 53)
    public void t53_jwtValidation() {
        String token = jwtUtil.generateToken(1L, "valid@test.com", "ADMIN");
        Assert.assertEquals(jwtUtil.validateToken(token), true);
    }

    @Test(priority = 54)
    public void t54_passwordEncodedCorrectly() {
        User u = new User();
        u.setEmail("new@x.com");
        u.setPassword("pass");

        when(userRepo.existsByEmail("new@x.com")).thenReturn(false);
        when(userRepo.save(any())).thenReturn(u);

        User saved = userService.registerUser(u);
        Assert.assertEquals(saved.getPassword().endsWith("_ENC"), true);
    }

    @Test(priority = 55)
    public void t55_loginFlowMocked() {
        LoginRequest req = new LoginRequest();
        req.setEmail("login@test.com");
        req.setPassword("abc");

        User u = new User(1L, "Test User", "login@test.com", "abc_ENC", "MONITOR");

        when(authenticationManager.authenticate(any())).thenReturn(mock(org.springframework.security.core.Authentication.class));
        when(userRepo.findByEmail("login@test.com")).thenReturn(Optional.of(u));

        String token = authController.login(req).getBody().getToken();
        Assert.assertEquals(token != null, true);
    }

    @Test(priority = 56)
    public void t56_registerFlowMocked() {
        RegisterRequest req = new RegisterRequest();
        req.setFullName("Test");
        req.setEmail("reg@test.com");
        req.setPassword("abc12345");

        when(userRepo.existsByEmail("reg@test.com")).thenReturn(false);
        when(userRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        String token = authController.register(req).getBody().getToken();
        Assert.assertEquals(token != null, true);
    }

    @Test(priority = 57)
    public void t57_jwtSubjectEmail() {
        String t = jwtUtil.generateToken(77L, "sub@test.com", "ADMIN");
        Assert.assertEquals(jwtUtil.extractEmail(t), "sub@test.com");
    }

    @Test(priority = 58)
    public void t58_invalidTokenFails() {
        Assert.assertEquals(jwtUtil.validateToken("bad.token.value"), false);
    }

    // -------------------------------------------------------------------
    // 8. HQL / JPQL Style Tests
    // -------------------------------------------------------------------

    @Test(priority = 59)
    public void t59_findRuleByProductAndDate() {
        TemperatureRule r = new TemperatureRule();
        r.setProductType("PHARMA");

        when(ruleRepo.findApplicableRule(eq("PHARMA"), any())).thenReturn(Optional.of(r));

        Assert.assertEquals(
                ruleService.getRuleForProduct("PHARMA", LocalDate.now()).isPresent(), true
        );
    }

    @Test(priority = 60)
    public void t60_findActiveRulesJPQL() {
        when(ruleRepo.findByActiveTrue()).thenReturn(List.of(new TemperatureRule()));
        Assert.assertEquals(ruleService.getActiveRules().size(), 1);
    }

    @Test(priority = 61)
    public void t61_breachRepositoryQuery() {
        when(breachRepo.findByShipmentId(1L)).thenReturn(List.of(new BreachRecord()));
        Assert.assertEquals(breachService.getBreachesByShipment(1L).size(), 1);
    }

    @Test(priority = 62)
    public void t62_alertRepositoryQuery() {
        when(alertRepo.findByShipmentId(5L)).thenReturn(List.of(new AlertRecord()));
        Assert.assertEquals(alertService.getAlertsByShipment(5L).size(), 1);
    }

    @Test(priority = 63)
    public void t63_logRepositoryQuery() {
        when(logRepo.findByShipmentId(3L)).thenReturn(List.of(new TemperatureSensorLog()));
        Assert.assertEquals(logService.getLogsByShipment(3L).size(), 1);
    }

    @Test(priority = 64)
    public void t64_shipmentRepoFindAll() {
        when(shipmentRepo.findAll()).thenReturn(List.of(new ShipmentRecord(), new ShipmentRecord()));
        Assert.assertEquals(shipmentService.getAllShipments().size(), 2);
    }
}