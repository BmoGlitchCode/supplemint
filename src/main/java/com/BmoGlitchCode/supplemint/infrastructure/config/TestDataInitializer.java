package com.BmoGlitchCode.supplemint.infrastructure.config;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.user.Email;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.user.PasswordEncoder;
import com.BmoGlitchCode.supplemint.domain.port.output.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Initializes test data for development and testing purposes.
 * Creates a default user, supplements, stacks, and sample logs.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInitializer {

    private final UserRepository userRepository;
    private final SupplementRepository supplementRepository;
    private final StackRepository stackRepository;
    private final SupplementLogRepository supplementLogRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeTestData() {
        log.info("Initializing test data...");

        // Create test user: Bmo Code
        User bmoUser = createTestUser();
        log.info("Created test user: {} (ID: {})", bmoUser.getEmail().getValue(), bmoUser.getId().getValue());

        // Create supplements
        Supplement vitaminC = createSupplement(bmoUser, "Vitamin C", "Immune system support",
                "NOW Foods", DosageType.PILL, new BigDecimal("1000"), DosageUnit.MG,
                BigDecimal.ONE, new BigDecimal("100"), new BigDecimal("85"),
                "Take with food for better absorption");

        Supplement vitaminD3 = createSupplement(bmoUser, "Vitamin D3", "Bone health and immune support",
                "Nature Made", DosageType.SOFTGEL, new BigDecimal("5000"), DosageUnit.IU,
                BigDecimal.ONE, new BigDecimal("180"), new BigDecimal("150"),
                "Take with fatty meal");

        Supplement fishOil = createSupplement(bmoUser, "Omega-3 Fish Oil", "Heart and brain health",
                "Nordic Naturals", DosageType.SOFTGEL, new BigDecimal("1000"), DosageUnit.MG,
                new BigDecimal("2"), new BigDecimal("120"), new BigDecimal("98"),
                "Contains EPA and DHA");

        Supplement magnesium = createSupplement(bmoUser, "Magnesium Glycinate", "Sleep and muscle recovery",
                "Doctor's Best", DosageType.PILL, new BigDecimal("200"), DosageUnit.MG,
                new BigDecimal("2"), new BigDecimal("240"), new BigDecimal("220"),
                "Take before bed");

        Supplement zinc = createSupplement(bmoUser, "Zinc Picolinate", "Immune support",
                "Thorne", DosageType.PILL, new BigDecimal("30"), DosageUnit.MG,
                BigDecimal.ONE, new BigDecimal("60"), new BigDecimal("55"),
                "Don't take with calcium");

        Supplement creatine = createSupplement(bmoUser, "Creatine Monohydrate", "Strength and performance",
                "Optimum Nutrition", DosageType.SCOOP, new BigDecimal("5"), DosageUnit.G,
                BigDecimal.ONE, new BigDecimal("83"), new BigDecimal("70"),
                "Mix with water or juice");

        Supplement proteinPowder = createSupplement(bmoUser, "Whey Protein Isolate", "Muscle recovery",
                "Dymatize", DosageType.SCOOP, new BigDecimal("25"), DosageUnit.G,
                BigDecimal.ONE, new BigDecimal("30"), new BigDecimal("22"),
                "Post workout");

        Supplement ashwagandha = createSupplement(bmoUser, "Ashwagandha KSM-66", "Stress and anxiety support",
                "Jarrow Formulas", DosageType.PILL, new BigDecimal("300"), DosageUnit.MG,
                new BigDecimal("2"), new BigDecimal("120"), new BigDecimal("100"),
                "Adaptogen - take daily");

        log.info("Created {} supplements", 8);

        // Create stacks
        Stack morningStack = createStack(bmoUser, "Morning Essentials",
                "Daily foundation supplements for energy and immunity",
                TimeOfDay.MORNING, "#FFD700",
                List.of(
                        StackItem.of(vitaminC.getId(), 0),
                        StackItem.of(vitaminD3.getId(), 1),
                        StackItem.of(fishOil.getId(), 2)
                ));

        Stack eveningStack = createStack(bmoUser, "Evening Recovery",
                "Supplements for sleep and recovery",
                TimeOfDay.EVENING, "#4169E1",
                List.of(
                        StackItem.of(magnesium.getId(), 0),
                        StackItem.of(zinc.getId(), 1),
                        StackItem.of(ashwagandha.getId(), 2)
                ));

        Stack workoutStack = createStack(bmoUser, "Workout Stack",
                "Pre and post workout supplements",
                TimeOfDay.ANYTIME, "#32CD32",
                List.of(
                        StackItem.of(creatine.getId(), 0),
                        StackItem.of(proteinPowder.getId(), 1)
                ));

        log.info("Created {} stacks", 3);

        // Create sample supplement logs
        Instant now = Instant.now();

        // Yesterday's logs
        createLog(bmoUser, vitaminC, morningStack, now.minus(1, ChronoUnit.DAYS), BigDecimal.ONE, false, "Taken with breakfast");
        createLog(bmoUser, vitaminD3, morningStack, now.minus(1, ChronoUnit.DAYS), BigDecimal.ONE, false, null);
        createLog(bmoUser, fishOil, morningStack, now.minus(1, ChronoUnit.DAYS), new BigDecimal("2"), false, null);
        createLog(bmoUser, magnesium, eveningStack, now.minus(1, ChronoUnit.DAYS).plus(12, ChronoUnit.HOURS), new BigDecimal("2"), false, "Before bed");

        // Two days ago
        createLog(bmoUser, vitaminC, morningStack, now.minus(2, ChronoUnit.DAYS), BigDecimal.ONE, false, null);
        createLog(bmoUser, vitaminD3, morningStack, now.minus(2, ChronoUnit.DAYS), BigDecimal.ONE, false, null);
        createLog(bmoUser, creatine, workoutStack, now.minus(2, ChronoUnit.DAYS).plus(6, ChronoUnit.HOURS), BigDecimal.ONE, false, "Pre-workout");
        createLog(bmoUser, proteinPowder, workoutStack, now.minus(2, ChronoUnit.DAYS).plus(8, ChronoUnit.HOURS), BigDecimal.ONE, false, "Post-workout shake");

        // A skipped log example
        createLog(bmoUser, zinc, eveningStack, now.minus(2, ChronoUnit.DAYS), BigDecimal.ONE, true, "Forgot to take");

        log.info("Created {} sample supplement logs", 9);

        log.info("Test data initialization complete!");
        log.info("===========================================");
        log.info("Test User Login Credentials:");
        log.info("  Email: bmo@example.com");
        log.info("  Password: password123");
        log.info("===========================================");
    }

    private User createTestUser() {
        String encodedPassword = passwordEncoder.encode("password123");
        User user = User.of(
                Email.of("bmo@example.com"),
                encodedPassword,
                "Bmo",
                "Code"
        );
        return userRepository.save(user);
    }

    private Supplement createSupplement(User user, String name, String description, String brand,
                                        DosageType dosageType, BigDecimal dosagePerServing,
                                        DosageUnit dosageUnit, BigDecimal servingSize,
                                        BigDecimal totalUnits, BigDecimal remainingUnits, String notes) {
        Supplement supplement = Supplement.of(
                user.getId(),
                name,
                description,
                brand,
                dosageType,
                dosagePerServing,
                dosageUnit,
                servingSize,
                totalUnits,
                remainingUnits,
                notes
        );
        return supplementRepository.save(supplement);
    }

    private Stack createStack(User user, String name, String description,
                              TimeOfDay defaultTime, String color, List<StackItem> items) {
        Stack stack = Stack.of(
                user.getId(),
                name,
                description,
                defaultTime,
                color,
                items
        );
        return stackRepository.save(stack);
    }

    private void createLog(User user, Supplement supplement, Stack stack,
                           Instant takenAt, BigDecimal unitsTaken, boolean skipped, String notes) {
        SupplementLog log = skipped
                ? SupplementLog.ofSkipped(user.getId(), supplement.getId(),
                        stack != null ? stack.getId() : null, takenAt, unitsTaken, notes)
                : SupplementLog.of(user.getId(), supplement.getId(),
                        stack != null ? stack.getId() : null, takenAt, unitsTaken, notes);
        supplementLogRepository.save(log);
    }
}
