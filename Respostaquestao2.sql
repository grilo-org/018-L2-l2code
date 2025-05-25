
--# QUESTAO 1
SELECT
    p.id AS professor_id,
    p.title_id,
    p.department_id,
    SUM(TIMESTAMPDIFF(HOUR, cs.start_time, cs.end_time)) AS total_hours
FROM
    PROFESSOR p
         JOIN SUBJECT s ON p.id = s.taught_by
         JOIN CLASS c ON s.id = c.subject_id
         JOIN CLASS_SCHEDULE cs ON c.id = cs.class_id
GROUP BY
    p.id, p.title_id, p.department_id;

--# QUESTAO 2
SELECT
    r.room_id,
    r.building_id,
    cs.day_of_week,
    cs.start_time,
    cs.end_time
FROM
    ROOM r
        LEFT JOIN CLASS_SCHEDULE cs ON r.room_id = cs.room_id
WHERE
    cs.class_id IS NOT NULL
ORDER BY
    r.room_id, cs.day_of_week, cs.start_time;