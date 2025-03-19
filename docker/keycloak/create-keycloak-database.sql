DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'core_app_keycloak') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE core_app_keycloak');
   END IF;
END $$;

