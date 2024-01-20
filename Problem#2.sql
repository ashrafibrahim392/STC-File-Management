SELECT count(*) as count,  td.user_id, td.training_date,td.training_id, u.userName FROM training_details td
inner join user  u on u.user_id = td.user_id
 group by 
 td.user_id, td.training_date,td.training_id
 having count > 1
 order by td.training_date desc