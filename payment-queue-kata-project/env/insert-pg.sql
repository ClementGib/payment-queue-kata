INSERT INTO pqkapp.partners(alias, type, direction, flow_type, description, application)
VALUES
(
    'crm_to_pqk',
    'CLIENT',
    'I',
    'M',
    'input flow from CRM',
    '{
      "name": "CRM",
      "team": "CustomerPlatform",
      "version": "v2.1",
      "url": "https://crm.internal",
      "contact": "crm-team@domain.com"
    }'::jsonb
),
(
    'pqk_to_crm',
    'CLIENT',
    'O',
    'A',
    'output flow to CRM',
    '{
      "name": "CRM",
      "team": "CustomerPlatform",
      "version": "v2.1",
      "url": "https://crm.internal/api",
      "contact": "crm-team@domain.com"
    }'::jsonb
),
(
    'erp_to_pqk',
    'CLIENT',
    'I',
    'N',
    'input flow from ERP',
    '{
      "name": "ERP",
      "team": "FinanceTeam",
      "version": "3.4",
      "url": "https://erp.internal",
      "contact": "erp-support@domain.com"
    }'::jsonb
),
(
    'pqk_to_erp',
    'CLIENT',
    'O',
    'M',
    'output flow to ERP',
    '{
      "name": "ERP",
      "team": "FinanceTeam",
      "version": "3.4",
      "url": "https://erp.internal/api",
      "contact": "erp-support@domain.com"
    }'::jsonb
),
(
    'pqk_to_front_office',
    'EXTERNAL',
    'I',
    'N',
    'input from front office',
    '{
      "name": "FRONT OFFICE",
      "team": "FrontOfficeTeam",
      "version": "3.2",
      "url": "https://front.external/api",
      "contact": "front-support@domain.com"
    }'::jsonb
),
(
    'pqk_to_monitoring',
    'MONITORING',
    'O',
    'A',
    'output flow for monitoring',
    '{
      "name": "MONITORING",
      "team": "MonitoringTeam",
      "version": "2.9",
      "url": "https://monitoring/api",
      "contact": "monitoring-support@domain.com"
    }'::jsonb
),
(
    'unknown',
    'MONITORING',
    'I',
    'A',
    'input for errored messages',
    '{
      "name": "MONITORING",
      "team": "MonitoringTeam",
      "contact": "pqk-support@domain.com"
    }'::jsonb
);
