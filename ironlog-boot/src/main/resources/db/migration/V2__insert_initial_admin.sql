INSERT INTO sec.app_user (id, enabled,external_id,email,full_name,"password","role") VALUES
	 (nextval('sec.user_seq'), true,'0a78e3a2-c672-4208-9192-ec4afdd19296'::uuid,'admin@ironlog.com','Iron Log Admin','$2a$10$YLjZr.wrtRudCSO7ea.nG.BTV0Cl2p/f6wjx3QeqRMbRxjS3GXd4y','ROLE_ADMIN');
