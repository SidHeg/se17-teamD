from django.contrib import admin

# Register your models here.

from .models import professor, student, course, attendance

admin.site.register(professor)
admin.site.register(student)
admin.site.register(course)
admin.site.register(attendance)