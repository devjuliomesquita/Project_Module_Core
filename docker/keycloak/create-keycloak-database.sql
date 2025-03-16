DO $$ 
BEGIN 
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'core_app_keycloak') THEN 
      CREATE DATABASE core_app_keycloak; 
   END IF; 
END $$;
