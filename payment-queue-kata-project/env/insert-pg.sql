INSERT INTO pqkapp.partner(alias, type, direction, flow_type, description, application) VALUES (
             'cacib_crm',
             'CLIENT',
             'O',
             'M',
             'Flux sortant vers CRM',
             '{
               "name": "crm",
               "team": "CustomerPlatform",
               "version": "v2.1",
               "url": "https://crm.internal",
               "contact": "cacib-dev@domain.com"
             }'::jsonb);