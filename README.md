# Job Portal

The repo is created to build a spring boot application. This repo is developed with a primary
focuses on backend development only.

## Technologies and design
* Spring boot
* Hibernate
* Spring security
* Spring Validators
* Annotated controllers
* JSP and CSS

## Overview
This app deals with two roles: job recruiter and job seeker. A job seeker is someone who is
looking to apply for jobs and a job recruiter is someone who is trying to recruit job seekers.
Every job recruiter must be part of a company.

## Features
* A job seeker can create account and apply to multiple jobs.
* A job seeker can view their list of applications and their statuses.
* A job seeker can filter jobs based on job name.
* A job recruiter can view applications based on jobs that they have posted.
* A job recruiter can choose to select or reject a job seeker.
* All routes undergoes role based authentication and bounded by timed sessions. A job seeker
cannot access the view of a job recruiter and vice-versa.
