package com.turvo.bankingqueue.constant;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
@SuppressWarnings("rawtypes")
public enum TokenTypeEnum {
	
	WITHDRAW {
		public int getWaitTimeInMins() {
			return 5;
		}

		public Queue<Enum> getActionTimes() {
			return new LinkedList<>(Arrays.asList(ServiceType.ACC_VERIFICATION, ServiceType.CASH_WITHDRAW));
		}

	}, DEPOSIT {
		public int getWaitTimeInMins() {
			return 5;
		}
		public Queue<Enum> getActionTimes() {
			return new LinkedList<Enum>(Arrays.asList(ServiceType.ACC_VERIFICATION, ServiceType.CASH_WITHDRAW));
		}
	}, CHECK_DEPOSIT {
		public int getWaitTimeInMins() {
			return 10;
		}
		public Queue<Enum> getActionTimes() {
			return new LinkedList<>(Arrays.asList(ServiceType.ACC_VERIFICATION, ServiceType.CHECK_DEPOSIT));
		}
	}, ACCOUNT_CLOSE {
		public int getWaitTimeInMins() {
			return 15;
		}
		public Queue<Enum> getActionTimes() {
			return new LinkedList<Enum>(Arrays.asList(ServiceType.ACC_VERIFICATION, ServiceType.MANAGER_APPROVAL, ServiceType.ACC_CLOSE));
		}
	}, ACCOUNT_OPEN {
		public int getWaitTimeInMins() {
			return 15;
		}
		public Queue<Enum> getActionTimes() {
			return new LinkedList<>(Arrays.asList(ServiceType.DOC_VERIFICATION, ServiceType.MANAGER_APPROVAL, ServiceType.ACC_OPEN));
		}
	};

	public abstract int getWaitTimeInMins();

	public abstract Queue<Enum> getActionTimes();

}
