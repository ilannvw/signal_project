package data_management;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertGeneratorTest {
    private DataStorage mockDataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    void setUp() {
        mockDataStorage = mock(DataStorage.class);
        alertGenerator = new AlertGenerator(mockDataStorage);
    }

    @Test
    void testEvaluateDataWithHighHeartRate() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 101.0, "HeartRate", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(1, alerts.size());
        assertEquals("High Heart Rate", alerts.get(0).getCondition());
    }

    @Test
    void testEvaluateDataWithNormalHeartRate() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 80.0, "HeartRate", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertTrue(alerts.isEmpty());
    }

    @Test
    void testEvaluateDataWithMultipleAlerts() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 101.0, "HeartRate", System.currentTimeMillis()),
                new PatientRecord(1, 150.0, "BloodPressure", System.currentTimeMillis()),
                new PatientRecord(1, 85.0, "BloodPressure", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(2, alerts.size());
        assertEquals("High Heart Rate", alerts.get(0).getCondition());
        assertEquals("High Blood Pressure", alerts.get(1).getCondition());
    }

    @Test
    void testEvaluateDataWithNoRecords() {
        Patient mockPatient = mock(Patient.class);
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(Arrays.asList());

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertTrue(alerts.isEmpty());
    }

    @Test
    void testEvaluateDataWithLowBloodPressure() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 70.0, "BloodPressure", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(1, alerts.size());
        assertEquals("Low Blood Pressure", alerts.get(0).getCondition());
    }

    @Test
    void testEvaluateDataWithLowBloodSaturation() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 89.0, "Saturation", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(1, alerts.size());
        assertEquals("Low Blood Saturation", alerts.get(0).getCondition());
    }

    @Test
    void testEvaluateDataWithAbnormalECG() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 2.0, "ECG", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(1, alerts.size());
        assertEquals("Abnormal ECG", alerts.get(0).getCondition());
    }

    @Test
    void testEvaluateDataWithMultipleConditionsInSingleRecord() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 105.0, "HeartRate", System.currentTimeMillis()),
                new PatientRecord(1, 75.0, "BloodPressure", System.currentTimeMillis())
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(2, alerts.size());
        assertEquals("High Heart Rate", alerts.get(0).getCondition());
        assertEquals("Low Blood Pressure", alerts.get(1).getCondition());
    }

    @Test
    void testEvaluateDataWithBorderlineValues() {
        Patient mockPatient = mock(Patient.class);
        List<PatientRecord> records = Arrays.asList(
                new PatientRecord(1, 100.0, "HeartRate", System.currentTimeMillis()), // Exactly at threshold
                new PatientRecord(1, 140.0, "BloodPressure", System.currentTimeMillis()) // Exactly at threshold
        );
        when(mockPatient.getRecords(anyLong(), anyLong())).thenReturn(records);

        alertGenerator.evaluateData(mockPatient);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertTrue(alerts.isEmpty());
    }

    @Test
    void testEvaluateDataWithDifferentPatients() {
        Patient mockPatient1 = mock(Patient.class);
        Patient mockPatient2 = mock(Patient.class);
        List<PatientRecord> records1 = Arrays.asList(
                new PatientRecord(1, 105.0, "HeartRate", System.currentTimeMillis())
        );
        List<PatientRecord> records2 = Arrays.asList(
                new PatientRecord(2, 75.0, "BloodPressure", System.currentTimeMillis())
        );
        when(mockPatient1.getRecords(anyLong(), anyLong())).thenReturn(records1);
        when(mockPatient2.getRecords(anyLong(), anyLong())).thenReturn(records2);

        alertGenerator.evaluateData(mockPatient1);
        alertGenerator.evaluateData(mockPatient2);

        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(2, alerts.size());
        assertEquals("High Heart Rate", alerts.get(0).getCondition());
        assertEquals("Low Blood Pressure", alerts.get(1).getCondition());
    }
}