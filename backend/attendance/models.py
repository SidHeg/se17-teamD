from __future__ import unicode_literals
from django.db import models



class professor(models.Model):
    prof_id = models.CharField(max_length=50)
    prof_name = models.CharField(max_length=50)
    password = models.CharField(max_length=20)

class student(models.Model):
    stud_id = models.CharField(max_length=50)
    stud_name = models.CharField(max_length=50)
    password = models.CharField(max_length=20)

class course(models.Model):
    course_id = models.CharField(max_length=50)
    course_name = models.CharField(max_length=100)
    professor = models.ForeignKey(professor, on_delete=models.CASCADE)
    qrcode = models.CharField(max_length=20, null=True)
    latitude = models.CharField(max_length = 20, null=True)
    longitude = models.CharField(max_length = 20, null=True)
    nfc = models.CharField(max_length=20, null=True)

class attendance(models.Model):
	course = models.ForeignKey(course, on_delete=models.CASCADE)
	student = models.ForeignKey(student, on_delete=models.CASCADE)
	qrcode = models.DateTimeField('date published', null=True)
	gps = models.DateTimeField('date published', null=True)
	nfc = models.DateTimeField('date published', null=True)