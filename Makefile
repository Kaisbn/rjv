SLIDES_DIR	= slides
SUBJECT_DIR	= subjects

all:: subject

subject::
	${MAKE} -C ${SUBJECT_DIR}
	mv ${SUBJECT_DIR}/main.pdf ${SUBJECT_DIR}/subject.pdf

clean::
	${MAKE} -C ${SLIDES_DIR} clean
	${RM} ${SLIDES_DIR}/slides.pdf
	${MAKE} -C ${SUBJECT_DIR} clean
	${RM} ${SUBJECT_DIR}/subject.pdf
	${MAKE} -C ${FILES_DIR} clean
