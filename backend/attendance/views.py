from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader
from .models import professor, student, course, attendance
import json, sys, ast
import ast

def login(post):
	try:
		user_id = post['user_id'].strip().lower()
		password = post['password'].strip().lower()
	except KeyError:
		return "user_id_or_password_not_received"
	try:
		s1 =  student.objects.get(stud_id=user_id)
		if s1.password == password:
			return "student_login_successful"
		else:
			return "student_login_fail"
	except:
		a = 1
	try:
		p1 =  professor.objects.get(prof_id=user_id)
		if p1.password == password:
			return "professor_login_successful"
		else:
			return "professor_login_fail"
	except KeyError:
		return "user_id_unavailable"


def checkGPS(lat, lon):
	return False


def gps(post):
	try:
		user_id = post['user_id'].strip().lower()
		course_id = post['course_id'].strip()
		latittude = post['lat'].strip()
		longitude = post['long'].strip()
	except:
		return "gps_not_received"
	login = login(post)
	if login == "professor_login_successful":
		try:
			c1 = course.objects.filter(course_id=course_id, professor__prof_id=user_id).values()[0]
			c1.latittude = latittude
			c1.longitude = longitude
			c1.save()
			return "professor_gps_stored_sucessfully"
		except:
			return "professor_course_mismatch"
	elif login == "student_login_successful":
		c1 = course.objects.get(course_id=course_id).values()
		if checkGPS(c1.latittude, c1.longitude):
			a1 = c1.attendance_set.get(student__stud_id=user_id)
			a1.gps = timezone.now()
			a1.save()
			return "student_gps_attendance_successful"
		else:
			return "student_gps_attendance_failed"
	else:
		return login


def qrc(post):
	try:
		user_id = post['user_id'].strip().lower()
		text = post['text'].strip().lower()
		course_id = post['course_id'].strip().lower()
	except:
		return "qrc_not_received"

	login = login(post)

	if login == "professor_login_successful":
		c1 = course.objects.filter(course_id=course_id)[0]
		c1.qrcode = text
		c1.save()
		return "professor_qrc_stored_sucessfully"

	elif login == "student_login_successful":

		c1 = course.objects.filter(course_id=course_id)[0]
		if c1.qrcode == text:
			s1 = student.objects.get(stud_id = user_id)
			a1 = attendance.objects.create(course = c1, student=s1)[0]
			a1.qrcode = timezone.now()
			return "student_qrc_attendance_successful"
		else:
			return "student_qrc_attendance_failed"
	else:
		return login


def nfc(post):
	try:
		user_id = post['user_id'].strip().lower()
		text = post['text'].strip().lower()
		course_id = post['course_id'].strip().lower()
	except:
		return "nfc_not_received"

	login = login(post)

	if login == "professor_login_successful":
		c1 = course.objects.filter(course_id=course_id)[0]
		c1.nfc = text
		c1.save()
		return "professor_nfc_stored_sucessfully"

	elif login == "student_login_successful":

		c1 = course.objects.filter(course_id=course_id)[0]
		if c1.nfc == text:
			s1 = student.objects.get(stud_id = user_id)
			a1 = attendance.objects.create(course = c1, student=s1)[0]
			a1.nfc = timezone.now()
			return "student_nfc_attendance_successful"
		else:
			return "student_nfc_attendance_failed"
	else:
		return login

def index(request):
	try:
		if request.method == 'POST':
			received_data=json.loads(request.body)
			#return HttpResponse(received_data)
			try:
				status = received_data["status"].strip().lower()
				#return HttpResponse(status)
			except:
				return HttpResponse("status_unavailable %s" %received_data)
			try:
				if status == r'login':
					return HttpResponse(login(received_data))
				elif status == r'gps':
					return HttpResponse(gps(received_data))
				elif status == r'qrc':
					return HttpResponse(qrc(received_data))
				elif status == r'nfc':
					return "error"
					return HttpResponse(nfc(received_data))
				else:
					return HttpResponse("wrong_status")
			except:
				return HttpResponse(sys.exc_info()[0])
		else:
			return HttpResponse("Not a POST method")
	except:
		return HttpResponse("Hello, world. You're at the Attendance Management System. %s" %sys.exc_info()[0])