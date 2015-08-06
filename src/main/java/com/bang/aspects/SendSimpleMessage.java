/**
 * 
 */
package com.bang.aspects;

import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bang.model.Job;
import com.bang.model.ServiceProvider;
import com.bang.service.ServiceProviderService;
import com.bang.util.SendSMS;

/**
 * @author vengat.r
 *
 */
@Aspect
@Component
public class SendSimpleMessage {
	
	private static final Logger logger = Logger.getLogger(SendSimpleMessage.class);
	
	@Autowired
	ServiceProviderService serviceProviderService;
	
	@Pointcut("execution (* com.bang.service.JobService.create(..))")
	public void jobCreate() {};
	
	@Pointcut("execution (* com.bang.service.JobService.assign(..))")
	public void jobAssign() {};
	
	@Pointcut("execution (* com.bang.service.JobService.closeJob(..))")
	public void jobClose() {};
	
	@AfterReturning(pointcut = "jobCreate()", returning = "job")
	public void sendJobConfirmationToCustomer(Job job) {
		logger.info("********************************* @ sendJobConfirmationToCustomer" + job.getCustomerName());
		System.out.println("Send sms to customer....");
		String message = String.format("Hello %s, This is an acknowledgement for the job (Job Id: %d%n, Type : %s)raised by you. Our representative will get in touch with you shortly. Thanks,", job.getCustomerName(), job.getId(), job.getJobType().toString());
		SendSMS.sendSMSToCustomer(String.valueOf(job.getCustomerMobileNumber()), message);
		SendSMS.sendSMSToCustomer("9986020496", message);
		SendSMS.sendSMSToCustomer("9902177000", message);
	
	}
	
	@AfterReturning(pointcut = "jobCreate()", returning = "job")
	public void sendJobInfoToElgibleServiceProviders(Job job) {
		logger.info("********************************* @ sendJobInfoToElgibleServiceProviders" + job.getCustomerName());
		System.out.println("Send sms to eligible service providers....");
		List<ServiceProvider> serviceProviders = serviceProviderService.getByPincodesServicedAndJobTypeSignedUpFor(job.getPincode(), job.getJobType().toString());
		String message = String.format("Hello, Please check if you can take up this new job raised by you Mr.%s (Job Id: %d%n, Type : %s). Customer phone %d%n. Thanks,", job.getCustomerName(), job.getId(), job.getJobType().toString(), job.getCustomerMobileNumber());
		for (ServiceProvider sp : serviceProviders) {
			SendSMS.sendSMSToCustomer(String.valueOf(sp.getMobileNumber()), message);
			SendSMS.sendSMSToCustomer("9986020496", message);
			SendSMS.sendSMSToCustomer("9902177000", message);
		}
	}
	
	@AfterReturning(pointcut = "jobAssign()", returning = "job")
	public void sendJobAssignmentInfo(Job job) {
		logger.info("********************************* @ sendJobAssignmentInfo" + job.getCustomerName());
		System.out.println("Send sms to customer about assignment....");
		String message = String.format("Hello %s, Your job (Job Id: %d%n, Type : %s) has been assigned to %s , %d%n. Thanks,", job.getCustomerName(), job.getId(), job.getJobType().toString(), job.getServiceProviderName(), job.getServiceProviderMobileNumber());
		SendSMS.sendSMSToCustomer(String.valueOf(job.getCustomerMobileNumber()), message);
		SendSMS.sendSMSToCustomer("9986020496", message);
		SendSMS.sendSMSToCustomer("9902177000", message);
	}
	
	@AfterReturning(pointcut = "jobClose()", returning = "job")
	public void sendJobClosedInfo(Job job) {
		logger.info("********************************* @ sendJobAssignmentInfo" + job.getCustomerName());
		System.out.println("Send sms to customer about job closure....");
		String message = String.format("Hello %s, Your job (Job Id: %d%n, Type : %s) has been successfully completed by %s , %d%n. Thanks,", job.getCustomerName(), job.getId(), job.getJobType().toString(), job.getServiceProviderName(), job.getServiceProviderMobileNumber());
		SendSMS.sendSMSToCustomer(String.valueOf(job.getCustomerMobileNumber()), message);
		SendSMS.sendSMSToCustomer("9986020496", message);
		SendSMS.sendSMSToCustomer("9902177000", message);
	}

}
