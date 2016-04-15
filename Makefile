SLIDES_DIR	= slides
SUBJECT_DIR	= subjects
FILES_DIR	= files

all:: subject files

subject::
	${MAKE} -C ${SUBJECT_DIR}
	mv ${SUBJECT_DIR}/main.pdf ${SUBJECT_DIR}/subject.pdf

files::
	${MAKE} -C ${FILES_DIR}

clean::
	${MAKE} -C ${SLIDES_DIR} clean
	${RM} ${SLIDES_DIR}/slides.pdf
	${MAKE} -C ${SUBJECT_DIR} clean
	${RM} ${SUBJECT_DIR}/subject.pdf
	${MAKE} -C ${FILES_DIR} clean
