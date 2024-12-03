--Drop the 'contactperson' column from 'visitor' table if it exists
ALTER TABLE visitor DROP COLUMN IF EXISTS contactperson;


--Drop the 'outtime' column from 'visitor' table if it exists
ALTER TABLE visitor DROP COLUMN IF EXISTS outtime;


-- Insert records into Table 'facilities_portal_roles' if they don't already exist
INSERT INTO facilities_portal_roles (id, createdat, isactive, role)
VALUES 
    (1, now(), true, 'management'),
    (2, now(), true, 'office_admin'),
    (3, now(), true, 'system_admin'),
    (4, now(), true, 'security')
ON CONFLICT (id) DO NOTHING;


---- Insert records into Table 'facilities_dropdowns' if they don't already exist
INSERT INTO facilities_dropdowns (id, dropdowntype, isactive, value)
VALUES
	(1, 'purpose', true, 'Interview'),
	(2, 'purpose', true, 'New Joinee'),
	(3, 'purpose', true, 'Client'),
	(4, 'purpose', true, 'Others'),
	(5, 'material_type', true, 'It'),
	(6, 'material_type', true, 'Non-It'),
	(7, 'purpose', true, 'Facilities Maintenance')
ON CONFLICT (id) DO NOTHING;

-- Drop the 'material_checkout_log' table if it exists
DROP TABLE IF EXISTS material_checkout_log;
	
	--Drop the 'gatepassname' column from 'facilities_portal_material_checkout_gate_pass' table if it exists
ALTER TABLE facilities_portal_material_checkout_gate_pass DROP COLUMN IF EXISTS gatepassname;

-- Update records inserted before the item quantity feature in material module : set 'totalquantity' to 1 where it is NULL
UPDATE facilities_portal_material_checkout_items
SET totalquantity = 1
WHERE totalquantity IS NULL;

-- Update records inserted before the  item quantity feature in material module : set 'totalreturnedquantity' to 0 if 'actualreturndatetime' is NULL, else set to 1, where 'totalreturnedquantity' is NULL
UPDATE facilities_portal_material_checkout_items
SET totalreturnedquantity = CASE WHEN actualreturndatetime IS NULL THEN 0 ELSE 1 END
WHERE totalreturnedquantity IS NULL;
	