SELECT 'CREATE DATABASE core_app_keycloak'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'core_app_keycloak')\gexec
