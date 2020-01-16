package pl.pinakoteka.customersservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pinakoteka.customersservice.dto.MessageType;
import pl.pinakoteka.customersservice.entity.Appointment;
import pl.pinakoteka.customersservice.entity.Sms;
import pl.pinakoteka.customersservice.repository.AppointmentRepository;
import pl.pinakoteka.customersservice.repository.SmsRepository;
import pl.smsapi.BasicAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.SmsapiException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SmsService implements MessageService {

    private final SmsRepository smsRepository;
    private final AppointmentRepository appointmentRepository;

    @Value("${api.sms.client.login}")
    private String login;

    @Value("${api.sms.client.password}")
    private String password;

    @Override
    public void sendMessage(String recipient, String message, MessageType messageType) {
        int hourNow = LocalDateTime.now().getHour();
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
//        if (hourNow < 10 || hourNow > 20 || (dayOfWeek == DayOfWeek.SATURDAY && hourNow > 14)|| dayOfWeek == DayOfWeek.SUNDAY) {
        if (hourNow < 10 || hourNow > 20 || messageType == MessageType.SMS_STATUS ? ((dayOfWeek == DayOfWeek.SATURDAY && hourNow > 14) || dayOfWeek == DayOfWeek.SUNDAY) : dayOfWeek == DayOfWeek.SATURDAY) {
            Sms sms = new Sms();
            sms.setPhoneNumber(recipient);
            sms.setMessage(message);
            saveSMS(sms);
        } else {
            try {
                BasicAuthClient client = new BasicAuthClient(login, password);
                log.info("Sending SMS message \"{}\" to phone {}", message, recipient);
                SmsFactory smsApi = new SmsFactory(client);
                SMSSend action = smsApi.actionSend()
                        .setText(message)
                        .setTo(recipient);
                StatusResponse result = action.execute();

                for (MessageResponse status : result.getList()) {
                    log.info("sms status: {} {}", status.getNumber(), status.getStatus());
                }

            } catch (SmsapiException e) {
                log.error("Error when sending SMS message", e);
                Sms sms = new Sms();
                sms.setPhoneNumber(recipient);
                sms.setMessage(message);
                saveSMS(sms);
            }
        }
    }

    private void saveSMS(Sms sms) {
        smsRepository.save(sms);
    }

    @Scheduled(cron = "0 10 10-19/1 * * MON-FRI")
    @Scheduled(cron = "0 59 10-14/1 * * SAT")
    public void resendUndeliveredMessages() {
        List<Sms> allSms = smsRepository.findAll();
        allSms.forEach(sms -> {
            sendMessage(sms.getPhoneNumber(), sms.getMessage(), MessageType.SMS_STATUS);
            log.info("Wysłano sms o treści \"{}\" na nr telefonu: {}", sms.getMessage(), sms.getPhoneNumber());
            smsRepository.delete(sms);
        });
        log.info("Sprawdzenie w bazie czy istnieją smsy do wysłania oraz ich wysłanie");
    }

    @Scheduled(cron = "0 10 15 * * SUN-FRI")
    public void appointmentRemaining() {
        LocalDate today = LocalDate.now();
        Date tomorrow = Date.valueOf(today.plusDays(1));
        List<Appointment> appointments = appointmentRepository.findAllByAppointmentDate(tomorrow);
        appointments.forEach(a -> {
            int hour = a.getAppointmentTime().toLocalTime().getHour();
            sendMessage(a.getUser().getPhoneNumber(), "Przypominamy o umówionym spotkaniu, zapraszamy serdecznie jutro o godzinie " + hour + ":00.", MessageType.SMS_APPOINTMENT);
        });
    }
}